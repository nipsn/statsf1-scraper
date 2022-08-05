import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object DatatableExtractor {
  def apply(url: String): (Iterable[String], Iterable[String]) = {
    val doc = JsoupBrowser().get(url)
    // TODO: Some grand prix don't have quali data. IE Indianapolis 1953. 1963 upwards doesn't seem to have any issues
    val data  = (doc >> elementList(".datatable") >> extractor("tbody td", texts)).head
    val header = (doc >> elementList(".datatable") >> extractor("thead td", texts)).head
    (header, data)
  }
}

