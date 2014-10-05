package jacobshack.hadoop

import java.text.SimpleDateFormat
import java.util.Date

import com.twitter.scalding._
import jacobshack.avro.{ CmxLogEntry, CmxLogEntryAvroSource }
import jacobshack.helpers.MacAddress
import jacobshack.mongo.RawMongoSource

/**
 * @author Andreas C. Osowski
 */

case class Probe(ap: Long, rssi: Int, time: Long) extends Comparable[Probe] {
  override def compareTo(o: Probe) = time.compareTo(o.time)
}

case class ProbeWithDistance(probe: Probe, dist: Double)

case class ClientLocation(mac: Long, lat: Double, lon: Double, timestamp: Long)

case class CoordWithDistance(lat: Double, lon: Double, dist: Double)

trait Helpers {
  def apWithDist(ap: Long, dist: Double): CoordWithDistance = ap match {
    // Restaurant Bottom - Middle Table
    case 0x180a387f04L => CoordWithDistance(48.8545678411561, 2.3378820461221, dist)
    // Restaurant Bottom - Room
    case 0x180a387f56L => CoordWithDistance(48.8545484281327, 2.33793434919789, dist)
    // Restaurant Top - Private Room
    case 0x180a387f02L => CoordWithDistance(48.8545908941115, 2.33785987598822, dist)
    // Club Above Speaker
    case 0x180a38803aL => CoordWithDistance(48.8546248737623, 2.3378322890494, dist)
    // Club Entry
    case 0x180a3876d0L => CoordWithDistance(48.8546297270115, 2.33777596266009, dist)
    // Club Fumoir
    case 0x180a3876e8L => CoordWithDistance(48.854642963143, 2.33792147249915, dist)
    // Club on Top of Vent
    case 0x180a387d52L => CoordWithDistance(48.8546244325579, 2.33786983997561, dist)
  }

  def estimateLocation(a: CoordWithDistance, b: CoordWithDistance, c: CoordWithDistance): (Double, Double) = {
    def dotProduct[T <% Double](as: Iterable[T], bs: Iterable[T]) = {
      require(as.size == bs.size)
      (for ((a, b) <- as zip bs) yield a * b) sum
    }

    def normalize[T <% Double](vec: Iterable[T]) = {
      val len = Math.sqrt(vec.map(v => v * v).sum)

      //vec.map(v => v / len)
      len

    }

    def cross[T <% Double](as: Seq[T], bs: Seq[T]) = {
      val x = as(0)
      val y = as(1)
      val z = as(2)
      val ox = bs(0)
      val oy = bs(1)
      val oz = bs(2)
      Seq(
        (y * oz - z * oy),
        (z * ox - x * oz),
        (x * oy - y * ox)
      )
    }

    val earthR = 6371.0

    val xA = earthR * (Math.cos(Math.toRadians(a.lat)) * math.cos(Math.toRadians(a.lon)))
    val yA = earthR * (math.cos(Math.toRadians(a.lat)) * math.sin(Math.toRadians(a.lon)))
    val zA = earthR * (math.sin(Math.toRadians(a.lat)))

    val xB = earthR * (math.cos(Math.toRadians(b.lat)) * math.cos(Math.toRadians(b.lon)))
    val yB = earthR * (math.cos(Math.toRadians(b.lat)) * math.sin(Math.toRadians(b.lon)))
    val zB = earthR * (math.sin(Math.toRadians(b.lat)))

    val xC = earthR * (math.cos(Math.toRadians(c.lat)) * math.cos(Math.toRadians(c.lon)))
    val yC = earthR * (math.cos(Math.toRadians(c.lat)) * math.sin(Math.toRadians(c.lon)))
    val zC = earthR * (math.sin(Math.toRadians(c.lat)))

    val P1 = Seq(xA, yA, zA)
    val P2 = Seq(xB, yB, zB)
    val P3 = Seq(xC, yC, zC)

    val p2mp1 = P2.zip(P1).map(t => t._1 - t._2)
    val p3mp1 = P3.zip(P1).map(t => t._1 - t._2)

    val ex = p2mp1.map(v => v / normalize(p2mp1))

    val i = dotProduct(ex, p3mp1)

    val iex = ex.map(v => v * i)
    val p3mp1miex = p3mp1.zip(iex).map(t => t._1 - t._2)

    val ey = p3mp1miex.map(v => v / normalize(p3mp1miex))
    val ez = cross(ex, ey)
    val d = normalize(p2mp1)
    val j = dotProduct(ey, p3mp1)

    val x = (Math.pow(a.dist, 2) - Math.pow(b.dist, 2) + Math.pow(d, 2)) / (2 * d)
    val y = ((Math.pow(a.dist, 2) - Math.pow(c.dist, 2) + Math.pow(i, 2) + Math.pow(j, 2)) / (2 * j)) - ((i / j) * x)

    val z = Math.sqrt(Math.pow(a.dist, 2) - Math.pow(x, 2) - Math.pow(y, 2))

    val xex = ex.map(v => v * x)
    val yey = ey.map(v => v * y)
    val zez = ez.map(v => v * z)

    val p1xex = P1.zip(xex).map(v => v._1 + v._2)
    val p1xexyey = p1xex.zip(yey).map(v => v._1 + v._2)
    val p1xexyeyzez = p1xexyey.zip(zez).map(v => v._1 + v._2)

    val triPt = p1xexyeyzez

    val latRad = Math.asin(triPt(2) / earthR)
    val lonRad = Math.atan2(triPt(1), triPt(0))

    val lat = Math.toDegrees(latRad)
    val lon = Math.toDegrees(lonRad)

    (lat, lon)
  }
}

class TrilaterateJob(args: Args) extends Job(args) with Helpers {

  type ProbeFrame = Seq[Probe]

  val probeFrameDuration = 3 * 1000L // seconds
  val dateFormat = new SimpleDateFormat()

  val input = new CmxLogEntryAvroSource(args("input")).map('CmxLogEntry -> ('client, 'time, 'probe)) {
    avro: CmxLogEntry => (avro.getClientMac, -avro.getTimestamp, Probe(avro.getApMac, avro.getRssi, avro.getTimestamp))
  }

  // Group input by client.
  val clientLocations = input
    .groupBy('client) { entries =>
      entries.mapStream('probe -> 'frame) { probes: Iterator[Probe] =>
        probes.toSeq.view.sortBy(_.time).foldLeft(Seq[Seq[Probe]]()) {
          case (Nil, v) => Seq(Seq(v))
          case (x :: xs, p) if Math.abs(p.time - x.head.time) <= probeFrameDuration => Seq(p +: x) ++ xs
          case (xs, p) => Seq(p) +: xs
        }
      }

      /*entries.sortBy('probe).foldLeft[Seq[Seq[Probe]], Probe]('probe -> 'frame)(Seq()) {
      case (Nil, v) => Seq(Seq(v))
      case (x :: xs, p) if Math.abs(p.time - x.head.time) <= probeFrameDuration => Seq(p +: x) ++ xs
      case (xs, p) => Seq(p) +: xs
    }*/
    }
    //.flatMap('frame -> 'frames) { s: Seq[ProbeFrame] => s }
    .map('frame -> 'framesWithDistances) { frame: ProbeFrame =>

      frame.map { p =>
        // Source: http://www.ijitee.org/attachments/File/v2i2/A0359112112.pdf
        // Where n = path-loss exponent. We need to fake this :(
        // Where A (dBm)= signal strength at distance of 1m
        // RSSI (dBm) = -10n log10(d) + A
        // (RSSI(dBm) - A)/(-10n) = log10(d)
        // 10^((RSSI(dBm) - A)/(-10n) = d

        //FIXME let's hope that our RSSI value is in dBm ;)
        //FIXME let's assume that A = 50
        //FIXME let's assume that n = 3.1

        val rxSensitivity = -90
        val n = 3.2
        val A = 49 + rxSensitivity
        val rssi = p.rssi + rxSensitivity
        val dist = Math.pow(10, (A - rssi) / (10 * n))
        ProbeWithDistance(p, dist)
      }
    }
    // We need at least 3 probes for trilateration.
    .filter('framesWithDistances) { fwd: Seq[ProbeWithDistance] => fwd.length > 3 && fwd.map(_.probe).groupBy(_.ap).size > 3 }
    .map(('client, 'framesWithDistances) -> 'distance) { tup: (Long, Seq[ProbeWithDistance]) =>
      val timestamp = tup._2.map(_.probe.time).sum / tup._2.length

      val points: Stream[Seq[CoordWithDistance]] = tup._2.map(p => apWithDist(p.probe.ap, p.dist / 1000.0))
        .sortBy(p => p.dist * -1).permutations.map(_.take(3)).toStream

      val locs = points.map(ps => estimateLocation(ps(0), ps(1), ps(2)))
      val estimatedLoc = locs.find(l => !(l._1.isNaN || l._2.isNaN))

      val loc = estimatedLoc.getOrElse((-1.0, -1.0))

      ClientLocation(tup._1, loc._1, loc._2, timestamp)
    }.project('distance)
    .filter('distance) { d: ClientLocation => d.lat != -1 }

  //  clientLocations.write(new Csv("/tmp/bla.csv"))
  clientLocations.map('distance -> ('mac, 'lat, 'lon, 'time)) {
    d: ClientLocation => (MacAddress(d.mac).withSeparator(), d.lat, d.lon, dateFormat.format(new Date(d.timestamp)))
  }.project('mac, 'lat, 'lon, 'time).write(new Csv("/tmp/locations.csv"))

}
