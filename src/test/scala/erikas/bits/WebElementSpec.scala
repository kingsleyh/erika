package erikas.bits

import erikas.bits.WebElementResponses.anyResponse
import org.scalatest.{FreeSpec, Matchers}

class WebElementSpec extends FreeSpec with Matchers {

  "WebElement" - {

    "getText should return some text" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getTextResponse, () => element.get().getText) should be(Some("some text"))
    }

    "getAttribute should return the attribute" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getAttributeResponse, () => element.get()
        .getAttribute("href")) should be(Some("some attribute"))
    }

    "click should perform a click" in {
      val element = WebElementHelper()
      element.withPostResponse(anyResponse, () => element.get().click()).getPostRequest should be("""{"id":":wdc:1460015822532"}""")
    }

    "clear should perform a clear" in {
      val element = WebElementHelper()
      element.withPostResponse(anyResponse, () => element.get().clear()).getPostRequest should be("""{"id":":wdc:1460015822532","sessionId":"test-session-id"}""")
    }

    "isEnabled should return boolean" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.isEnabledResponse, () => element.get().isEnabled) should be(true)
    }


  }

}




