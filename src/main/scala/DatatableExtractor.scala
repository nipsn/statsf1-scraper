import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

object DatatableExtractor {

  var header: Iterable[String] = Iterable[String]()
  var data: Iterable[String] = Iterable[String]()

  def apply(url: String): (Iterable[String], Iterable[String]) = {
    try {
      val doc = JsoupBrowser().get(url)
      data  = (doc >> elementList(".datatable") >> extractor("tbody td", texts)).head
      header = (doc >> elementList(".datatable") >> extractor("thead td", texts)).head
    } catch {
      case e: Exception => println(e + ": Some race didn't have quali data")
    }

    (header, data)

  }
}

