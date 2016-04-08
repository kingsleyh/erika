package erikas.bits

object PhantomRequests {

  val create = """{"desiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""},"javascriptEnabled":true,"handleAlerts":true,"takeScreenshot":true,"platform":"OSX","cssSelectorsEnabled":true},"requiredCapabilities":{"browserConnectionEnabled":true,"databaseEnabled":true,"nativeEvents":true,"webStorageEnabled":true,"applicationCacheEnabled":true,"locationContextEnabled":true,"acceptSslCerts":true,"browserName":"phantomjs","version":"phantomjs","rotatable":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""},"javascriptEnabled":true,"handleAlerts":true,"takeScreenshot":true,"platform":"OSX","cssSelectorsEnabled":true}}"""

  val visitUrl = """{"url":"http://some-url"}"""

  val findElementById = """{"using":"id","value":"some-id"}"""

  val findElementByClassName = """{"using":"class name","value":"some-classname"}"""
}
