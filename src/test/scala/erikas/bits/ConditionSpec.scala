package erikas.bits

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

  }

}




