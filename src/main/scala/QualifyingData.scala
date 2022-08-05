import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object QualifyingData {
  def apply(season: Int): Unit = {
    val url = "https://www.statsf1.com/en/" + season.toString + ".aspx"
    val doc = JsoupBrowser().get(url)
    val raceUrls  = (doc >> elementList(".gp .flag") >> attr("href")("a")).map(_.dropRight(5))
      .map(url => (url, url.substring(9)))

    raceUrls.foreach(url => {
      val diskName = "quali_" + url._2 + "_1984"
      val (header, data) = DatatableExtractor("https://www.statsf1.com" + url._1 + "/qualification.aspx")
      val csvString = (header :: data.grouped(header.size).toList)
        .map (_.mkString(","))
        .filter(line => !line.contains("Not qualified"))
        .mkString("\n")

        ParsedTableWriter(diskName, csvString)
      }
    )
  }
}
