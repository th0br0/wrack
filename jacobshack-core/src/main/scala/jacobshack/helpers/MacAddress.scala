package jacobshack.helpers

import java.lang.{ Long => JLong }

/**
 * @author Andreas C. Osowski
 */
// TODO this shouldn't be in a trait

class MacAddress(mac: String) {
  def asString = mac
  lazy val asLong = {
    val hex = "0123456789abcdef"
    mac.toLowerCase.filterNot(_ == ':').toSeq.map(hex.indexOf(_).toLong).reduceLeft(_ * 16L + _)
  }

  def withSeparator(separator: String = ":") = mac.grouped(2).mkString(separator)

}
object MacAddress {
  def apply(mac: String) = new MacAddress(mac)
  def apply(mac: Long) = {
    val hex = JLong.toHexString(mac)
    new MacAddress("0" * (12 - hex.length) + hex)
  }

  def fromSeparator(mac: String, sep: Char) = apply(mac.filterNot(_ == sep))

  def unapply(add: MacAddress): Long = add.asLong
}
