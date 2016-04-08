package erikas.bits

object LocatorStrategy extends Enumeration {
  val CLASS_NAME = Value("class name")
  val CSS_SELECTOR = Value("css selector")
  val ID = Value("id")
  val NAME = Value("name")
  val LINK_TEXT = Value("link text")
  val PARTIAL_LINK_TEXT = Value("partial link text")
  val TAG_NAME = Value("tag name")
  val XPATH = Value("xpath")
}

case class By(strategy: LocatorStrategy.Value, value: String) {
  val locatorStrategy = strategy.toString
}


object By {

  def id(value: String) = new By(LocatorStrategy.ID, value)
  def className(value: String) = new By(LocatorStrategy.CLASS_NAME, value)

}


