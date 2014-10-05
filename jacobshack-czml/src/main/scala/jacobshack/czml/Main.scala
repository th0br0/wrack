package jacobshack.czml

import java.awt.Color
import java.io.{ File, PrintWriter, StringWriter }
import java.text.SimpleDateFormat
import java.util.Date

import cesiumlanguagewriter._
import org.joda.time.DateTime

import scala.io.Source
import java.lang.{ Double => JDouble }

import scala.util.{ Failure, Success, Try }

/**
 * @author Andreas C. Osowski
 */

case class ClientLocation(mac: String, lat: Double, lon: Double, timestamp: Date)

object Main extends App {
  def writeAPlocs(writer: CesiumStreamWriter) = {
    val locs = Seq(("00:18:0a:38:7f:04", 48.8545678411561, 2.3378820461221),
      ("00:18:0a:38:7f:56", 48.8545484281327, 2.33793434919789),
      ("00:18:0a:38:7f:02", 48.8545908941115, 2.33785987598822),
      ("00:18:0a:38:80:3a", 48.8546248737623, 2.3378322890494),
      ("00:18:0a:38:76:d0", 48.8546297270115, 2.33777596266009),
      ("00:18:0a:38:76:e8", 48.854642963143, 2.33792147249915),
      ("00:18:0a:38:7d:52", 48.8546244325579, 2.33786983997561))

    //FIXME why are lat & lon the other way round ... weird.

    for ((loc, idx) <- locs.zipWithIndex) {
      val ent = writer.openPacket(output)
      ent.writeId("ap #" + idx);

      val pos = ent.openPositionProperty()
      pos.writeCartographicDegrees(new Cartographic(loc._3, loc._2, 0.0))
      pos.close()

      val pt = ent.openPointProperty()
      pt.writePixelSizeProperty(10.0)
      pt.writeColorProperty(Color.WHITE)
      pt.close

      val desc = ent.openDescriptionProperty()
      desc.writeString(
        "<table class=\"cesium-infoBox-defaultTable\"><tbody>" +
          "<tr><td>AP mac address</td><td>" + loc._1 + "</td></tr>" +
          "<tr><td>Longitude</td><td>" + loc._3 + " degrees</td></tr>" +
          "<tr><td>Latitude</td><td>" + loc._2 + " degrees</td></tr>" +
          "</tbody></table>");
      desc.close

      ent.close()
    }
  }

  def writeClientLoc(writer: CesiumStreamWriter, loc: ClientLocation, startTimestamp: Date, lastTimestamp: Date, alpha: Float) = {
    val ent = writer.openPacket(output)
    ent.writeId(s"point ${loc.timestamp} $lastTimestamp $alpha");

    val pos = ent.openPositionProperty()
    pos.writeCartographicDegrees(new Cartographic(loc.lon, loc.lat, 0.0))
    pos.close()

    val pt = ent.openPointProperty()
    pt.writePixelSizeProperty(10.0)
    val col = new Color(1f, 1f, 0f, alpha)
    pt.writeColorProperty(col)
    pt.close

    ent.writeAvailability(new JulianDate(new DateTime(startTimestamp)), new JulianDate(new DateTime(lastTimestamp)))

    val desc = ent.openDescriptionProperty()
    desc.writeString(
      "<table class=\"cesium-infoBox-defaultTable\"><tbody>" +
        "<tr><td>Mac address</td><td>" + loc.mac + "</td></tr>" +
        "<tr><td>Timestamp</td><td>" + loc.timestamp + "</td></tr>" +
        "<tr><td>Longitude</td><td>" + loc.lon + " degrees</td></tr>" +
        "<tr><td>Latitude</td><td>" + loc.lat + " degrees</td></tr>" +
        "</tbody></table>");
    desc.close

    ent.close()

  }

  val dateFormat = new SimpleDateFormat()
  val locs = Source.fromFile(args(0)).getLines().toSeq.map { line =>
    val parts = line.split(",")
    ClientLocation(
      parts(0),
      JDouble.parseDouble(parts(1)),
      JDouble.parseDouble(parts(2)),
      Try(dateFormat.parse(parts(3))) match {
        case Success(d) => d
        case Failure(_) => new Date()
      }
    )
  }

  val clients = locs.groupBy(_.mac)
  val sortedCount = clients.toSeq.sortBy(c => -1 * c._2.length).map(c => {
    val locs = c._2.sortBy(_.timestamp)
    locs
  })

  val cesiumWriter = new CesiumStreamWriter()
  val stringWriter = new StringWriter()
  val output = new CesiumOutputStream(stringWriter)
  output.setPrettyFormatting(true)
  output.writeStartSequence()
  val ent = cesiumWriter.openPacket(output)
  ent.writeId("document")
  ent.writeVersion("1.0")
  ent.close()

  writeAPlocs(cesiumWriter)

  val x = sortedCount.head
  val xs = x.tail.padTo(x.length, null)
  val xss = xs.tail.padTo(x.length, null)

  val xz = (x, xs, xss).zipped

  for (
    (c, n, f) <- xz
  ) {
    val until = n match {
      case null => c.timestamp
      case n => n.timestamp
    }

    writeClientLoc(cesiumWriter, c, c.timestamp, until, 0.5f)
    if (n != null)
      writeClientLoc(cesiumWriter, n, c.timestamp, until, 0.75f)
    if (f != null)
      writeClientLoc(cesiumWriter, f, c.timestamp, until, 1f)
  }

  output.writeEndSequence()
  val pw = new PrintWriter(new File("/tmp/out.czml"))
  pw.write(stringWriter.getBuffer.toString)
  pw.close
}
