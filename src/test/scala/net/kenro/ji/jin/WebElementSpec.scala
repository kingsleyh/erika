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

    "isSelected should return boolean" in {
      val element = WebElementHelper()
      element.withGetResponses(List(WebElementResponses.isSelectedResponse))
      element.get().isSelected should be(true)
    }

    "sendKeys should sent text" in {
      val element = WebElementHelper()
      element.withPostResponse("", () => element.get().sendKeys("hello")).getRequestUrl should be("/session/test-session-id/element/:wdc:1460015822532/value")
      element.testDriver.getPostRequest should be("""{"value":["h","e","l","l","o"]}""")
    }


    "Sub element types" - {

      "TextInput" - {

        "toTextInput should return a text input when type text" in {
          val element = WebElementHelper()
          element.withGetResponses(List(WebElementResponses.nameResponseWith("input"), WebElementResponses.attributeResponseWith("text")))
          element.get().toTextInput shouldBe a[TextInput]
        }

        "toTextInput should return a text input when type password" in {
          val element = WebElementHelper()
          element.withGetResponses(List(WebElementResponses.nameResponseWith("input"), WebElementResponses.attributeResponseWith("password")))
          element.get().toTextInput shouldBe a[TextInput]
        }

        "toTextInput should return a text input when type email" in {
          val element = WebElementHelper()
          element.withGetResponses(List(WebElementResponses.nameResponseWith("input"), WebElementResponses.attributeResponseWith("email")))
          element.get().toTextInput shouldBe a[TextInput]
        }


        "getValue should get value" in {
          val helper = WebElementHelper()
          val item = TextInput("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("some-value")
          ))
          item.getValue should be("some-value")
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/attribute/value")
        }

        "setValue should set value" in {
          val helper = WebElementHelper()
          val item = TextInput("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.setValue("some-value"))
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/value")
        }

        "clearValue should clear value" in {
          val helper = WebElementHelper()
          val item = TextInput("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.clearValue)
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/clear")
        }

      }

      "TextArea" - {

        "toTextArea should return a text area" in {
          val element = WebElementHelper()
          element.withGetResponses(List(WebElementResponses.nameResponseWith("textarea")))
          element.get().toTextArea shouldBe a[TextArea]
        }

        "getValue should get value" in {
          val helper = WebElementHelper()
          val item = TextArea("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("some-value")
          ))
          item.getValue should be("some-value")
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/attribute/value")
        }

        "setValue should set value" in {
          val helper = WebElementHelper()
          val item = TextArea("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.setValue("some-value"))
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/value")
        }

        "clearValue should clear value" in {
          val helper = WebElementHelper()
          val item = TextArea("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.clearValue)
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/clear")
        }

      }

      "Button" - {

        "toButton should return a button when input submit" in {
          val element = WebElementHelper()
          element.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("submit")
          ))
          element.get().toButton shouldBe a[Button]
        }

        "toButton should return a button when type button" in {
          val element = WebElementHelper()
          element.withGetResponses(List(
            WebElementResponses.nameResponseWith("button")
          ))
          element.get().toButton shouldBe a[Button]
        }

        "getValue should get value" in {
          val helper = WebElementHelper()
          val item = Button("elementId", "sessionId", "sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("some-value")
          ))
          item.getValue should be("some-value")
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/attribute/value")
        }

        "click should send click" in {
          val helper = WebElementHelper()
          val item = Button("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.click())
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/click")
        }

      }

      "Link" - {

        "toLink should return a link" in {
          val element = WebElementHelper()
          element.withGetResponses(List(
            WebElementResponses.nameResponseWith("a")
          ))
          element.get().toLink shouldBe a[Link]
        }

        "getText should get link text" in {
          val helper = WebElementHelper()
          val item = Link("elementId", "sessionId", "sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("some-value")
          ))
          item.getText should be("some-value")
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/attribute/text")
        }

        "click should send click" in {
          val helper = WebElementHelper()
          val item = Link("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.click())
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/click")
        }

      }

      "Radio" - {

        "toRadio should return a radio" in {
          val element = WebElementHelper()
          element.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio")
          ))
          element.get().toRadio shouldBe a[Radio]
        }

        "getValue should get value" in {
          val helper = WebElementHelper()
          val item = Radio("elementId", "sessionId", "sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("some-value")
          ))
          item.getValue should be("some-value")
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/attribute/value")
        }

        "click should send click" in {
          val helper = WebElementHelper()
          val item = Radio("elementId","sessionId","sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponseAction(WebElementResponses.getBooleanResponse, () => item.click())
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/click")
        }

        "getSelected should send selected" in {
          val helper = WebElementHelper()
          val item = Radio("elementId", "sessionId", "sessionUrl", helper.testDriver, helper.session)
          helper.withGetResponses(List(
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getBooleanResponse
          ))
          item.isSelected should be(true)
          helper.testDriver.getRequestUrl should be("sessionUrl/element/elementId/selected")
        }

      }

      "RadioMulti" - {

        "toRadioMulti should return a list of radios" in {

          import ListOfWebElementUtils.ListDecorator

          val helper = WebElementHelper()
          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.attributeResponseWith("some-name"),
            WebElementResponses.attributeResponseWith("some-name"),
            WebElementResponses.attributeResponseWith("some-name"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio")
          ))

          val elements = helper.getAll()
          elements.toRadioMulti.getRadios shouldBe a[List[Radio]]
        }

        "getSelected should send select" in {
          val helper = WebElementHelper()
          val item = RadioMulti(helper.getAll())

          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getBooleanResponse
          ))

          item.getSelected shouldBe a [Some[Radio]]
          helper.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460215713666/selected")
        }

        "getSelectedValue should get value" in {
          val helper = WebElementHelper()
          val item = RadioMulti(helper.getAll())

          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getValueResponse("a")
          ))

          item.getSelectedValue should be("a")
          helper.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460215713666/attribute/value")
        }

        "hasSelected should get boolean" in {
          val helper = WebElementHelper()
          val item = RadioMulti(helper.getAll())

          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.getBooleanResponse,
            WebElementResponses.getBooleanResponse
          ))

          item.hasSelected should be(true)
          helper.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460215713666/selected")
        }

        "getByValue should get value" in {
          val helper = WebElementHelper()
          val item = RadioMulti(helper.getAll())

          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.getValueResponse("a")
          ))

          item.getByValue("a") shouldBe a [Some[Radio]]
          helper.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460215713666/attribute/value")
        }

        "selectByValue should send click" in {
          val helper = WebElementHelper()
          val item = RadioMulti(helper.getAll())

          helper.withGetResponses(List(
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.nameResponseWith("input"),
            WebElementResponses.attributeResponseWith("radio"),
            WebElementResponses.getValueResponse("a"),
            WebElementResponses.getBooleanResponse
          ))

          item.selectByValue("a")
          helper.testDriver.getRequestUrl should be("/session/test-session-id/element/:wdc:1460215713666/click")
        }

      }

    }


  }

}




