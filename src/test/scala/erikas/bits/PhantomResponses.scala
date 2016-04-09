package erikas.bits

object PhantomResponses {

  val anyResponse = """{"sessionId":"261fc340-fe76-11e5-88a9-11420d9826e2","status":0,"value":{}}"""

  val sessionResponse = """{"sessionId":"test-session-id"}"""

  val visitUrlResponse = """{"sessionId":"test-session-id","status":0,"value":{}}"""

  val findElementResponse = """{"sessionId":"test-session-id","status":0,"value":{"ELEMENT":":wdc:1460015822532"}}"""

  val findElementsResponse = """{"sessionId":"b6eab5b0-fe67-11e5-88a9-11420d9826e2","status":0,"value":[{"ELEMENT":":wdc:1460215713666"},{"ELEMENT":":wdc:1460215713667"}]}"""

  val getSessionsResponse = """{"sessionId":null,"status":0,"value":[{"id":"82bed430-fdcf-11e5-ab4d-4bc17e26c21d","capabilities":{"browserName":"phantomjs","version":"1.9.2","driverName":"ghostdriver","driverVersion":"1.0.4","platform":"mac-unknown-32bit","javascriptEnabled":true,"takesScreenshot":true,"handlesAlerts":false,"databaseEnabled":false,"locationContextEnabled":false,"applicationCacheEnabled":false,"browserConnectionEnabled":false,"cssSelectorsEnabled":true,"webStorageEnabled":false,"rotatable":false,"acceptSslCerts":false,"nativeEvents":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""}}}]}"""

  val getStatusResponse = """{"sessionId":null,"status":0,"value":{"build":{"version":"1.0.4"},"os":{"name":"mac","version":"unknown","arch":"32bit"}}}"""

  val getCapabilitiesResponse = """{"sessionId":"74806b30-fe75-11e5-88a9-11420d9826e2","status":0,"value":{"browserName":"phantomjs","version":"1.9.2","driverName":"ghostdriver","driverVersion":"1.0.4","platform":"mac-unknown-32bit","javascriptEnabled":true,"takesScreenshot":true,"handlesAlerts":false,"databaseEnabled":false,"locationContextEnabled":false,"applicationCacheEnabled":false,"browserConnectionEnabled":false,"cssSelectorsEnabled":true,"webStorageEnabled":false,"rotatable":false,"acceptSslCerts":false,"nativeEvents":true,"proxy":{"socksUsername":"","httpProxy":"","socksProxy":"","noProxy":"","proxyType":"","sslProxy":"","proxyAutoconfigUrl":"","socksPassword":"","ftpProxy":""}}}"""

  val getWindowHandlesResponse = """{"sessionId":"2c6c9480-fe8f-11e5-ab4d-4bc17e26c21d","status":0,"value":["2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d"]}"""

  val getWindowHandleResponse = """{"sessionId":"2c6c9480-fe8f-11e5-ab4d-4bc17e26c21d","status":0,"value":"2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d"}"""

  val getUrlResponse = """{"sessionId":"b3fb3a40-fe90-11e5-ab4d-4bc17e26c21d","status":0,"value":"http://someurl.com/"}"""

  val getSourceResponse = """{"sessionId":"184375b0-fe93-11e5-88a9-11420d9826e2","status":0,"value":"source"}"""

  val getTitleResponse = """{"sessionId":"184375b0-fe93-11e5-88a9-11420d9826e2","status":0,"value":"title"}"""



}
