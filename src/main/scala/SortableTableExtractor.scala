import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._

import java.io.PrintWriter

object SortableTableExtractor {
  def apply(url: String, diskName: String): Unit = {
    val (header, data) = extractTable(url)
    ParsedTableWriter(diskName, header, data)
  }

  def extractTable(url: String): (Iterable[String], Iterable[String]) = {
    val doc = JsoupBrowser().get(url)
    val data  = (doc >> elementList(".sortable") >> extractor("tbody td", texts)).head
    val header = (doc >> elementList(".sortable") >> extractor("thead td", texts)).head
    (header, data)
  }
}
