package net.kenro.ji.jin

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

case class By(strategy: LocatorStrategy.Value, value: String) extends Searcher {
  val locatorStrategy = strategy.toString
}

object By {

  def id(value: String) = new By(LocatorStrategy.ID, value)

  def className(value: String) = new By(LocatorStrategy.CLASS_NAME, value)

  def cssSelector(value: String) = new By(LocatorStrategy.CSS_SELECTOR, value)

  def name(value: String) = new By(LocatorStrategy.NAME, value)

  def linkText(value: String) = new By(LocatorStrategy.LINK_TEXT, value)

  def partialLinkText(value: String) = new By(LocatorStrategy.PARTIAL_LINK_TEXT, value)

  def tagName(value: String) = new By(LocatorStrategy.TAG_NAME, value)

  def xpath(value: String) = new By(LocatorStrategy.XPATH, value)

}


