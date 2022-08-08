import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._


object QualifyingData {
  def apply(season: Int): Boolean = {
    val url = "https://www.statsf1.com/en/" + season.toString + ".aspx"
    val doc = JsoupBrowser().get(url)
    val raceUrls  = (doc >> elementList(".gp .flag") >> attr("href")("a")).map(_.dropRight(5))
      .map(url => (url, url.substring(9)))

    // not a parallel collection bc it takes actually longer
    // too many threads
    raceUrls.map(url => {
        val csvString = getRaceQualiData("https://www.statsf1.com" + url._1 + "/qualification.aspx")
        ParsedTableWriter(season, "/quali_" + url._2, csvString)
      }
    ).foldLeft(true)((l, n) => l && n)
  }

  def getRaceQualiData(url: String): String = {
    val (header, data) = DatatableExtractor(url)
    (header :: data.grouped(header.size).toList)
      .map (_.mkString(","))
      .filter(line => !line.matches(",Not (pre-)?qualified,,,,,,"))
      .mkString("\n")
      .replaceAll("''", ".")
      .replace('\'', ':')
  }

}
