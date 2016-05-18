package net.kenro.ji.jin

class WebElementHelper {

  val (session, testDriver) = SessionHelper()

  private val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                        .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(By.id("some-id")))

  private val elements = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                         .withPostResponseAction(PhantomResponses.findElementsResponse, () => session.findElements(By.id("some-id")))

  def get() = element

  def getAll() = elements

  def withGetResponseAction[T](cannedResponse: String, action: () => T): T = testDriver.withGetResponseAction(cannedResponse, action)

  def withPostResponseAction[T](cannedResponse: String, action: () => T): T = testDriver.withPostResponseAction(cannedResponse, action)

  def withPostResponse(cannedResponse: String, action: () => Unit): TestDriver = testDriver.withPostResponse(cannedResponse, action)

  def withGetResponses(cannedResponses: List[String], action: () => Unit = () => {}): TestDriver = testDriver.withGetResponses(cannedResponses, action)

  def withPostResponses(cannedResponses: List[String], action: () => Unit = () => {}): TestDriver = testDriver.withPostResponses(cannedResponses, action)

}

object WebElementHelper {
  def apply() = new WebElementHelper()
}