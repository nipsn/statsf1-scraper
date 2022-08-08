object Main {
  def main(args: Array[String]): Unit = {
    (1950 to 2022).toList.foreach(ssn => {
      println(ssn)
      QualifyingData(ssn)
    })
  }
}