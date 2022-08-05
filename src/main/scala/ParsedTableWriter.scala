import java.io.PrintWriter

object ParsedTableWriter {
  def apply(diskName: String, csv: String): Unit = {
    new PrintWriter("src/main/resources/ " + diskName + ".csv") {
      write(
        csv
      ); close() }
  }
}
