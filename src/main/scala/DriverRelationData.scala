import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object DriverRelationData {
  def apply(): Unit = {
    val csvString = "Driver,Teammates\n" + ('a' to 'z').toList.flatMap(getDriverNamesAndLinks)
      .map(x => x._1 + "," + getTeammatesFromDriver(x._2).mkString(";"))
      .mkString("\n")
    println(csvString)
    ParsedTableWriter.writeTeammateData(csvString)
  }

  def getDriverNames(doc: JsoupBrowser): List[String] = {
    ???
  }

  def getTeammatesFromDriver(url: String): List[String] = {
    val doc = JsoupBrowser().get("https://www.statsf1.com" + url + "/coequipier.aspx")
    (doc >> elementList("tbody a")).map(_ >> allText("a"))
      .filter(_ != "")
      .zipWithIndex
      .filter(_._2 % 2 == 0)
      .map(_._1)
  }

  def getDriverNamesAndLinks(letter: Char): List[(String, String)] = {
    val url = "https://www.statsf1.com/en/pilotes-" + letter.toString + ".aspx"
    val doc = JsoupBrowser().get(url)
    val driverNames  = (doc >> elementList("tbody a")).map(_ >> allText("a"))
      .filter(_ != "")
      .zipWithIndex
      .filter(_._2 % 2 == 0)
      .map(_._1)

    val driverLinks = (doc >> elementList("tbody a") >> attr("href")("a"))
      .filter(!_.contains("victoire"))
      .zipWithIndex
      .filter(_._2 % 2 == 0)
      .map(_._1.dropRight(5))

    driverNames zip driverLinks
  }
}
