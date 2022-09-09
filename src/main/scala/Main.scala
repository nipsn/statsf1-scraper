import scala.collection.parallel.CollectionConverters._

object Main {
  def main(args: Array[String]): Unit = {
    val start = System.nanoTime
    if ((1950 to 2022).toList.par.map(processSeason).foldLeft(true)(_ && _)) {
      println("Retrieved all quali data")
    } else {
      println("Something went wrong")
    }

    processDrivers()

    println("Took " + (System.nanoTime - start) / 1e9d)
  }




  def processSeason(ssn: Int): Boolean = {
    println("Searching data for season " + ssn)
    val res = QualifyingData(ssn)
    println("Data retrieved for season " + ssn)
    res
  }

  def processDrivers(): Unit = {
    println("Searching data for drivers")
    DriverRelationData()
    println("Data retrieved for all drivers")
  }
}