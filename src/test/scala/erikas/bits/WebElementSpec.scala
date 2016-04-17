package erikas.bits

import erikas.bits.WebElementResponses.anyResponse
import org.scalatest.{FreeSpec, Matchers}

class WebElementSpec extends FreeSpec with Matchers {

  "WebElement" - {

    "getText should return some text" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getTextResponse, () => element.get().getText) should be(Some("some text"))
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/text")
    }

    "getAttribute should return the attribute" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getAttributeResponse, () => element.get()
        .getAttribute("href")) should be(Some("some attribute"))
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/attribute/href")
    }

    "click should perform a click" in {
      val element = WebElementHelper()
      element.withPostResponse(anyResponse, () => element.get().click()).getPostRequest should be("""{"id":":wdc:1460015822532"}""")
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/click")
    }

    "clear should perform a clear" in {
      val element = WebElementHelper()
      element.withPostResponse(anyResponse, () => element.get().clear()).getPostRequest should be("""{"id":":wdc:1460015822532","sessionId":"test-session-id"}""")
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/clear")
    }

    "isEnabled should return boolean" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.isEnabledResponse, () => element.get().isEnabled) should be(true)
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/enabled")
    }


  }

}




