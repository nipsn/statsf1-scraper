object Main {
  def main(args: Array[String]): Unit = {
    (1964 to 2021).toList.foreach(ssn => {
      println(ssn)
      QualifyingData(ssn)
    })

  }
}