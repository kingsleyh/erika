package net.kenro.ji.jin

object PhantomRequests {

  val create = """{"desiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"takesScreenshot":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"javascriptEnabled":true,"handlesAlerts":true,"platform":"MAC","cssSelectorsEnabled":true},"requiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"takesScreenshot":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"javascriptEnabled":true,"handlesAlerts":true,"platform":"MAC","cssSelectorsEnabled":true}}"""

  val visitUrl = """{"url":"http://some-url"}"""

  val findElementById = """{"using":"id","value":"some-id"}"""

  val findActiveElement = """{"sessionId":"test-session-id"}"""

  val findElementByClassName = """{"using":"class name","value":"some-classname"}"""

  val findElementByCssSelector = """{"using":"css selector","value":".some-selector"}"""

  val findElementByTagName = """{"using":"tag name","value":"tagname"}"""

  val findElementByPartialLinkText = """{"using":"partial link text","value":"some partial text"}"""

  val findElementByLinkText = """{"using":"link text","value":"some link text"}"""

  val findElementByName = """{"using":"name","value":"name"}"""

  val findElementByXpath = """{"using":"xpath","value":"//*path"}"""

  val executeScript = """{"script":"function(){ return 1+1;}","args":[]}"""

  val setTimeout = """{"type":"page load","ms":1000}"""

  val setTimeoutValue = """{"ms":1000}"""
}
