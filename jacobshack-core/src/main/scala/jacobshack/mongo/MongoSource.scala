package jacobshack.mongo

import cascading.flow.FlowProcess
import cascading.scheme.{ Scheme, SinkCall, SourceCall }
import cascading.tap.Tap
import cascading.tap.hadoop.io.HadoopTupleEntrySchemeIterator
import cascading.tuple._
import com.mongodb.hadoop.mapred.{ MongoInputFormat, BSONFileInputFormat }
import com.mongodb.hadoop.io.BSONWritable
import com.mongodb.hadoop.util.MongoConfigUtil
import com.twitter.scalding._
import org.apache.hadoop.io.{ NullWritable, WritableComparable }
import org.apache.hadoop.mapred.{ FileInputFormat, JobConf, OutputCollector, RecordReader }

import MongoTypes._
import org.bson.BSONObject
import org.bson.types.ObjectId

/**
 * @author Andreas C. Osowski
 */

object MongoTypes {
  type MongoOutputCollector = OutputCollector[ObjectId, BSONObject]
  type MongoReader = RecordReader[WritableComparable[NullWritable], BSONWritable]
}

class MongoSourceTap[C](scheme: MongoDBScheme) extends Tap[JobConf, MongoReader, MongoOutputCollector](scheme) {

  override def getIdentifier: String = scheme.uri

  override def deleteResource(conf: JobConf): Boolean = true

  override def resourceExists(conf: JobConf): Boolean = true

  override def getModifiedTime(conf: JobConf): Long = System.currentTimeMillis()

  override def createResource(conf: JobConf): Boolean = true

  override def openForWrite(flowProcess: FlowProcess[JobConf], output: MongoOutputCollector): TupleEntryCollector = ???

  override def openForRead(flowProcess: FlowProcess[JobConf], input: MongoReader): TupleEntryIterator = flowProcess match {
    case c: FlowProcess[JobConf] => new HadoopTupleEntrySchemeIterator(flowProcess.asInstanceOf[FlowProcess[JobConf]], this, input)
    // case c: FlowProcess[Properties] => new TupleEntrySchemeIterator(flowProcess, scheme, input)
  }

}

class MongoDBScheme(val uri: String, columnNames: List[String])
    extends Scheme[JobConf, MongoReader, MongoOutputCollector, Array[Object], Array[BSONWritable]] {

  override def isSource = true

  override def getSourceFields: Fields = new Fields(columnNames.toSeq: _*)

  override def sourceConfInit(flowProcess: FlowProcess[JobConf], tap: Tap[JobConf, MongoReader, MongoOutputCollector], conf: JobConf): Unit = {
    MongoConfigUtil.setReadSplitsFromShards(conf, true)

    if (!uri.startsWith("mongodb://")) {
      conf.setInputFormat(classOf[BSONFileInputFormat])
      FileInputFormat.setInputPaths(conf, uri)
    } else {
      MongoConfigUtil.setInputURI(conf, uri)
      MongoConfigUtil.setAuthURI(conf, uri)
      conf.setInputFormat(classOf[MongoInputFormat])
    }

  }

  override def sourcePrepare(flowProcess: FlowProcess[JobConf], sourceCall: SourceCall[Array[Object], MongoReader]): Unit = {
    sourceCall.setContext(new Array[Object](2))
    sourceCall.getContext.update(0, sourceCall.getInput.createKey)
    sourceCall.getContext.update(1, sourceCall.getInput.createValue)
  }

  override def source(flowProcess: FlowProcess[JobConf], sourceCall: SourceCall[Array[Object], MongoReader]): Boolean = {
    val result = new Tuple

    val key = sourceCall.getContext()(0).asInstanceOf[WritableComparable[NullWritable]]
    val value = sourceCall.getContext()(1).asInstanceOf[BSONWritable]

    if (!sourceCall.getInput().next(key, value)) {
      return false
    }

    columnNames.foreach(c =>
      result.add(Option(value.getDoc.get(c)).getOrElse(""))
    )

    sourceCall.getIncomingEntry.setTuple(result)
    true
  }

  override def sink(flowProcess: FlowProcess[JobConf], sinkCall: SinkCall[Array[BSONWritable], MongoOutputCollector]): Unit = ???

  override def sinkConfInit(flowProcess: FlowProcess[JobConf], tap: Tap[JobConf, MongoReader, MongoOutputCollector], conf: JobConf): Unit = ???
}

trait MongoSource {
  this: Source =>
  def scheme: MongoDBScheme
  override def createTap(readOrWrite: AccessMode)(implicit mode: Mode): Tap[_, _, _] = readOrWrite match {
    case Write => ???
    case Read => new MongoSourceTap(scheme)
  }
}