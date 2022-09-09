import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import StringUtils.StringExtensions


object QualifyingData {
  def apply(season: Int): Boolean = {
    val url = "https://www.statsf1.com/en/" + season.toString + ".aspx"
    val doc = JsoupBrowser().get(url)
    val raceUrls  = (doc >> elementList(".gp .flag") >> attr("href")("a")).map(_.dropRight(5))
      .map(url => (url, url.substring(9))).zipWithIndex

    // not a parallel collection bc it takes actually longer
    // too many threads
    raceUrls.map(url => {
        val csvString = getRaceQualiData("https://www.statsf1.com" + url._1._1 + "/qualification.aspx", url._2, season)
        ParsedTableWriter.writeSeasonData(season, "/quali_" + url._1._2, csvString)
      }
    ).foldLeft(true)(_ && _)
  }

  def getRaceQualiData(url: String, raceNbOnSsn: Int, season: Int): String = {
    val (header, data) = DatatableExtractor(url)

    // raceId is defined by the season and the order in which races took place
    // first race of 2000 season would have id "200001", 14th race would have "200014"
    val modifiedHeader = ("raceId" :: header.toList).mkString(",")

    val dataWithId = data.grouped(header.size).toList
      .map(_.mkString(","))
      .filter(line => !line.matches(",Not (pre-)?qualified,,,,,,"))
      .map(line => season + (raceNbOnSsn + 1).toString.lpad(2, "0") + "," + line)

    (modifiedHeader :: dataWithId)
      .mkString("\n")
      .replaceAll("''", ".")
      .replace('\'', ':')
  }

}
