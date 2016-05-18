package net.kenro.ji.jin

import org.scalatest.{FreeSpec, Matchers}

class ConditionSpec extends FreeSpec with Matchers {

  "Condition" - {

    "titleIs should check if page title matches supplied title" in {
      Condition.titleIs("some-title").isSatisfied(List(StubWebElement().withAttribute("returns false"))) should be(false)
      Condition.titleIs("some-title").isSatisfied(List(StubWebElement().withAttribute("some-title"))) should be(true)
    }

    "attributeContains should check if the attribute matches the supplied value" in {
      Condition.attributeContains("name", "Kingsley").isSatisfied(List(StubWebElement().withAttribute("returns false"))) should be(false)
      Condition.attributeContains("name", "Kingsley").isSatisfied(List(StubWebElement().withAttribute("Kingsley"))) should be(true)
    }

    "textContains should check if the text matches the supplied value" in {
      Condition.textContains("Kingsley").isSatisfied(List(StubWebElement().withText("returns false"))) should be(false)
      Condition.textContains("Kingsley").isSatisfied(List(StubWebElement().withText("Kingsley"))) should be(true)
    }

    "textStartsWith should check if the text starts with the supplied value" in {
      Condition.textStartsWith("Kingsley").isSatisfied(List(StubWebElement().withText("returns false"))) should be(false)
      Condition.textStartsWith("Kingsley").isSatisfied(List(StubWebElement().withText("Kingsley is the king"))) should be(true)
    }

    "textEndsWith should check if the text end with the supplied value" in {
      Condition.textEndsWith("Kingsley").isSatisfied(List(StubWebElement().withText("returns false"))) should be(false)
      Condition.textEndsWith("the king").isSatisfied(List(StubWebElement().withText("Kingsley is the king"))) should be(true)
    }

    "isClickable should check the element is clickable" in {
      Condition.isClickable.isSatisfied(List(StubWebElement().withPresent(false).withEnabled(false))) should be(false)
      Condition.isClickable.isSatisfied(List(StubWebElement().withPresent(true).withEnabled(true))) should be(true)
    }

    "isPresent should check the element is present" in {
      Condition.isPresent.isSatisfied(List(StubWebElement().withPresent(false))) should be(false)
      Condition.isPresent.isSatisfied(List(StubWebElement().withPresent(true))) should be(true)
    }

    "isEnabled should check the element is enabled" in {
      Condition.isEnabled.isSatisfied(List(StubWebElement().withEnabled(false))) should be(false)
      Condition.isEnabled.isSatisfied(List(StubWebElement().withEnabled(true))) should be(true)
    }

    "isVisible should check the element is visible" in {
      Condition.isVisible.isSatisfied(List(StubWebElement().withDisplayed(false))) should be(false)
      Condition.isVisible.isSatisfied(List(StubWebElement().withDisplayed(true))) should be(true)
    }

  }

}




