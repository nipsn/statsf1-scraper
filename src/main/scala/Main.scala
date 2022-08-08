import scala.collection.parallel.CollectionConverters._

object Main {
  def main(args: Array[String]): Unit = {
    (1950 to 2022).toList.par.foreach(ssn => {
      processSeason(ssn)
    })
  }


  def processSeason(ssn: Int): Unit = {
    println("Searching data for season " + ssn)
    QualifyingData(ssn)
    println("Data retrieved for season " + ssn)

  }
}