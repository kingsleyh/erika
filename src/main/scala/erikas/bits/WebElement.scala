package erikas.bits

import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: PhantomDriver, session: Session) {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def getText:String = {
    val el = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text"))
    println(el.entityAsString)
    el.decode[StringResponse].value
  }

}
