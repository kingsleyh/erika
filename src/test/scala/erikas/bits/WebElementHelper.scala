package erikas.bits

class WebElementHelper {

  val (session, testDriver) = SessionHelper()

  private val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                        .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(By.id("some-id")))

  def get() = element

  def withGetResponseAction[T](cannedResponse: String, action: () => T): T = testDriver.withGetResponseAction(cannedResponse, action)
}

object WebElementHelper {
  def apply() = new WebElementHelper()
}