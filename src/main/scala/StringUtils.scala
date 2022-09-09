object StringUtils{
  implicit class StringExtensions(str: String) {
    def lpad(len: Int, elem: String): String = {
      elem*(len - str.length) + str
    }
  }
}
