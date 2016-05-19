package net.kenro.ji.jin

object WebElementResponses {

  val anyResponse = """{"anything":"whatever"}"""

  val getTextResponse = """{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":"some text"}"""

  def getValueResponse(value: String) = s"""{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":"$value"}"""

  val getBooleanResponse = """{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":true}"""

  val getAttributeResponse = """{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":"some attribute"}"""

  val isEnabledResponse = """{"sessionId":"01819690-fdc6-11e5-9493-0d9253df0313","status":0,"value":true}"""

  val isDisplayedResponse = """{"sessionId":"01819690-fdc6-11e5-9493-0d9253df0313","status":0,"value":true}"""

  val isSelectedResponse = """{"sessionId":"01819690-fdc6-11e5-9493-0d9253df0313","status":0,"value":true}"""

  val findElementResponse = """{"sessionId":"test-session-id","status":0,"value":{"ELEMENT":":wdc:1460015822532"}}"""

  def attributeResponseWith(value: String) = s"""{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":"$value"}"""

  def nameResponseWith(name: String) = s"""{"sessionId":"08a55cc0-fd64-11e5-ad95-9f9f4876f484","status":0,"value":"$name"}"""

}
