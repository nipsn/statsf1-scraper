object Main {
  def main(args: Array[String]): Unit = {
    val url = "https://www.statsf1.com/en/statistiques/pilote/pole/nombre.aspx"
    SortableTableExtractor(url, "mostpolepositions")
  }
}