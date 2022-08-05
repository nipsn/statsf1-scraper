import java.io.{File, PrintWriter}

object ParsedTableWriter {
  def apply(season: Int, fileName: String, csv: String): Unit = {
    val directory = new File("src/main/resources/" + season)
    if (!directory.exists){
      directory.mkdir
    }

    new PrintWriter("src/main/resources/" + season + fileName + ".csv") {
      write(
        csv
      ); close() }
  }
}
