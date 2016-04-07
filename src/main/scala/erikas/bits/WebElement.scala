package erikas.bits

import erikas.bits.Driver.handleFailedRequest

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: PhantomDriver, session: Session) {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

//  public string getText()
//  {
//    HttpResponse response = driver.doGet(elementSessionUrl ~ "/text");
//    handleFailedRequest(elementSessionUrl, response);
//    StringResponse stringResponse = parseJSON(response.content).fromJSON!StringResponse;
//    return stringResponse.value;
//  }

//  def getText():String = {
//    val response = driver.doGet(s"$elementSessionUrl/text")
//    handleFailedRequest(elementSessionUrl, response)
//
//  }

}
