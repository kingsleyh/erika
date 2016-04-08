package erikas.bits

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


  }

}




