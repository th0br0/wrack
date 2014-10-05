package jacobshack.mongo

import java.util.Date

import com.twitter.scalding.Source

/**
 * @author Andreas C. Osowski
 */

class RawMongoSource(mongoUrl: String) extends Source with MongoSource {
  val scheme = new MongoDBScheme(
    mongoUrl,
    List("client_mac", "ap_mac", "rssi", "last_seen")
  )

  // FIXME this needs to happen automatically...
  //def parsedDates = .map(('last_seen, 'client_mac) ->('date, 'client)) { t: (Date, String) => t}
}