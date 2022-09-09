import java.io.{File, PrintWriter}

object ParsedTableWriter {
  def writeSeasonData(season: Int, fileName: String, csv: String): Boolean = {
    val directory = new File("src/main/resources/" + season)
    if (!directory.exists) {
      directory.mkdir
    }

    new PrintWriter("src/main/resources/" + season + fileName + ".csv") {
      write(csv)
      close()
    }

    new File("src/main/resources/" + season + fileName + ".csv").exists
  }

  def writeTeammateData(csv: String): Unit = {
    new PrintWriter("src/main/resources/teammate_data.csv") {
      write(csv)
      close()
    }
  }
}
