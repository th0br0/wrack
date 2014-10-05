package jacobshack.avro

import com.twitter.scalding.avro.PackedAvroSource
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificRecordBase

/**
 * @author Andreas C. Osowski
 */

// TODO these should be part of some other project

sealed abstract class AvroSource[T <: SpecificRecordBase](path: String)(implicit m: Manifest[T]) extends PackedAvroSource[T](Seq(path)) {
  // FIXME get rid of the cast
  lazy val classSchema = m.runtimeClass.newInstance.asInstanceOf[SpecificRecordBase].getSchema

  override def schema: Schema = classSchema

  override def hdfsPaths: List[String] = List(path)

  override def localPath: String = path
}

class CmxLogEntryAvroSource(path: String) extends AvroSource[CmxLogEntry](path)
