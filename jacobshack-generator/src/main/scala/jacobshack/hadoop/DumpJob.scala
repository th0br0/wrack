package jacobshack.hadoop

import jacobshack.avro.{ CmxLogEntryAvroSource }
import com.twitter.scalding.{ Args, Csv, Job, Source }

/**
 * @author Andreas C. Osowski
 */

// FIXME maybe move this to presence-core?
class DumpJob(args: Args) extends Job(args) {

  val what = args("what")

  val source: Source = {
    if (what.startsWith("avro")) {
      what match {
        case "avro_cmx" => new CmxLogEntryAvroSource(args("input"))
      }
    } else null
  }

  source.write(new Csv(args("output")))
}
