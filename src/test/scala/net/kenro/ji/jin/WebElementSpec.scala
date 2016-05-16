package net.kenro.ji.jin

import net.kenro.ji.jin.WebElementResponses.anyResponse
import org.scalatest.{FreeSpec, Matchers}

class WebElementSpec extends FreeSpec with Matchers {

  "WebElement" - {

    "getText should return some text" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getTextResponse, () => element.get().getText) should be("some text")
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/text")
    }

    "getAttribute should return the attribute" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.getAttributeResponse, () => element.get()
        .getAttribute("href")) should be("some attribute")
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

    "isDisplayed should return boolean" in {
      val element = WebElementHelper()
      element.withGetResponseAction(WebElementResponses.isDisplayedResponse, () => element.get().isDisplayed) should be(true)
      element.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/displayed")
    }

    "isPresent should return boolean" in {
      val element = WebElementHelper()
      element.withGetResponses(List(WebElementResponses.isEnabledResponse, WebElementResponses.isDisplayedResponse))
      element.get().isPresent should be(true)
    }

    "sendKeys should sent text" in {
      val element = WebElementHelper()
      element.withPostResponse("", () => element.get().sendKeys("hello")).getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/value")
      element.testDriver.getPostRequest should be("""{"value":["h","e","l","l","o"]}""")
    }


//    "Sub element types" - {
//
//      "toTextInput should return a text input" in {
//        val element = WebElementHelper()
//        element.withPostResponse(WebElementResponses.findElementResponse)
//      }
//
//    }


  }

}




