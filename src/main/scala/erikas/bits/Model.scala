package erikas.bits

import argonaut._, Argonaut._

case class Proxy(proxyType: String = "",
                 proxyAutoconfigUrl: String = "",
                 ftpProxy: String = "",
                 httpProxy: String = "",
                 sslProxy: String = "",
                 socksProxy: String = "",
                 socksUsername: String = "",
                 socksPassword: String = "",
                 noProxy: String = "")
object Proxy {

  implicit def ProxyJson =
     casecodec9(Proxy.apply, Proxy.unapply)("proxyType", "proxyAutoconfigUrl","ftpProxy","httpProxy","sslProxy","socksProxy",
       "socksUsername","socksPassword","noProxy")
}

case class Capabilities(browserName: String = "phantomjs",
                        platform: String = "OSX",
                        version: String = "phantomjs",
                        javascriptEnabled: Boolean = true,
                        takeScreenshot: Boolean = true,
                        handlesAlerts: Boolean = true,
                        databaseEnabled: Boolean = true,
                        locationContextEnabled: Boolean = true,
                        applicationCacheEnabled: Boolean = true,
                        browserConnectionEnabled: Boolean = true,
                        cssSelectorsEnabled: Boolean = true,
                        webStorageEnabled: Boolean = true,
                        rotatable: Boolean = true,
                        acceptSslCerts: Boolean = true,
                        nativeEvents: Boolean = true,
                        proxy: Proxy = Proxy())

object Capabilities {

  implicit def CapabilitiesJson =
      casecodec16(Capabilities.apply, Capabilities.unapply)("browserName", "platform", "version", "javascriptEnabled", "takeScreenshot", "handleAlerts",
      "databaseEnabled","locationContextEnabled","applicationCacheEnabled","browserConnectionEnabled","cssSelectorsEnabled","webStorageEnabled",
      "rotatable", "acceptSslCerts", "nativeEvents", "proxy")
}

case class RequestSession(desiredCapabilities: Capabilities, requiredCapabilities: Capabilities)

object RequestSession {

  implicit def RequestSessionJson =
    casecodec2(RequestSession.apply, RequestSession.unapply)("desiredCapabilities", "requiredCapabilities")
}

case class RequestUrl(url: String)

object RequestUrl {
  implicit def RequestUrlJson =
     casecodec1(RequestUrl.apply, RequestUrl.unapply)("url")
}

case class SessionResponse(sessionId: String)

object SessionResponse {
  implicit def SessionResponseJson =
    casecodec1(SessionResponse.apply, SessionResponse.unapply)("sessionId")
}

case class RequestFindElement(using: String, value: String)

object RequestFindElement {
  implicit def RequestFindElementJson =
    casecodec2(RequestFindElement.apply, RequestFindElement.unapply)("using","value")
}

