package jacobshack.hadoop

import java.util.Date

import com.twitter.scalding.{ Args, Job }
import jacobshack.avro.{ CmxLogEntryAvroSource, CmxLogEntry }
import jacobshack.helpers.MacAddress
import jacobshack.mongo.RawMongoSource

/**
 * @author Andreas C. Osowski
 */
class FilterNetworkJob(args: Args) extends Job(args) {

  val mongo = new RawMongoSource(args("mongo"))
  //net: 6//val aps = Seq("00:18:0a:38:75:28", "00:18:0a:38:7a:c8", "00:18:0a:38:7b:4a", "00:18:0a:38:7c:02", "00:18:0a:38:7f:4e", "00:18:0a:38:7c:b8")
  // net:1,2
  val aps = Seq( //"00:18:0a:38:7f:56", "00:18:0a:38:7f:04", "00:18:0a:38:7f:02", //"00:18:0a:38:80:0e",
    "00:18:0a:38:80:3a", "00:18:0a:38:76:d0", "00:18:0a:38:76:e8", "00:18:0a:38:7d:52")

  mongo.filter('ap_mac) { mac: String => aps.contains(mac) }
    .map(('client_mac, 'ap_mac, 'rssi, 'last_seen) -> 'avro) {
      row: (String, String, Int, Date) =>
        new CmxLogEntry(6, MacAddress(row._2).asLong, MacAddress(row._1).asLong, row._3, row._4.getTime)
    }.project('avro)
    .write(new CmxLogEntryAvroSource(args("avro")))

}
