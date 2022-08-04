import java.io.PrintWriter

object ParsedTableWriter {
  def apply(diskName: String, header: Iterable[String], data: Iterable[String]): Unit = {
    new PrintWriter("src/main/resources/ " + diskName + ".csv") {
      write(
        (header :: data.grouped(header.size).toList)
          .map {
            _.mkString(",")
          }.mkString("\n")
      ); close() }
  }
}
