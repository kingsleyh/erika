package erikas.bits

object PhantomRequests {

  val create = """{"desiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"takesScreenshot":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""},"javascriptEnabled":true,"handlesAlerts":true,"platform":"OSX","cssSelectorsEnabled":true},"requiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"takesScreenshot":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""},"javascriptEnabled":true,"handlesAlerts":true,"platform":"OSX","cssSelectorsEnabled":true}}"""

  val visitUrl = """{"url":"http://some-url"}"""

  val findElementById = """{"using":"id","value":"some-id"}"""

  val findElementByClassName = """{"using":"class name","value":"some-classname"}"""

  val findElementByCssSelector = """{"using":"css selector","value":".some-selector"}"""

  val findElementByTagName = """{"using":"tag name","value":"tagname"}"""

  val findElementByPartialLinkText = """{"using":"partial link text","value":"some partial text"}"""

  val findElementByLinkText = """{"using":"link text","value":"some link text"}"""

  val findElementByName = """{"using":"name","value":"name"}"""

  val findElementByXpath = """{"using":"xpath","value":"//*path"}"""
}
