import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object DatatableExtractor {
  def apply(url: String): (Iterable[String], Iterable[String]) = {
    val doc = JsoupBrowser().get(url)
    val data  = (doc >> elementList(".datatable") >> extractor("tbody td", texts)).head
    val header = (doc >> elementList(".datatable") >> extractor("thead td", texts)).head
    (header, data)
  }

}

