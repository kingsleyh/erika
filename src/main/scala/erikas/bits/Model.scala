package erikas.bits

import argonaut.Argonaut._

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
    casecodec9(Proxy.apply, Proxy.unapply)("proxyType", "proxyAutoconfigUrl", "ftpProxy", "httpProxy", "sslProxy", "socksProxy",
      "socksUsername", "socksPassword", "noProxy")
}

case class Capabilities(browserName: String = "phantomjs",
                        platform: String = "OSX",
                        version: String = "phantomjs",
                        javascriptEnabled: Boolean = true,
                        takesScreenshot: Boolean = true,
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
    casecodec16(Capabilities.apply, Capabilities.unapply)("browserName", "platform", "version", "javascriptEnabled", "takesScreenshot", "handlesAlerts",
      "databaseEnabled", "locationContextEnabled", "applicationCacheEnabled", "browserConnectionEnabled", "cssSelectorsEnabled", "webStorageEnabled",
      "rotatable", "acceptSslCerts", "nativeEvents", "proxy")
}

case class SessionRequest(desiredCapabilities: Capabilities, requiredCapabilities: Capabilities)

object SessionRequest {
  implicit def Encoder =
    jencode2L((o: SessionRequest) => (o.desiredCapabilities, o.requiredCapabilities))("desiredCapabilities", "requiredCapabilities")
}

case class UrlRequest(url: String)

object UrlRequest {
  implicit def Encoder =
    jencode1L((o: UrlRequest) => o.url)("url")
}

case class CreateSessionResponse(sessionId: String)

object CreateSessionResponse {
  implicit def Decoder =
    jdecode1L(CreateSessionResponse.apply)("sessionId")
}

case class Sessions(id: String, capabilities: Capabilities)

object Sessions {
  implicit def Decoder = jdecode2L(Sessions.apply)("id", "capabilities")
}

case class SessionResponse(sessionId: Option[String], status: Int, value: List[Sessions])

object SessionResponse {
  implicit def Decoder = jdecode3L(SessionResponse.apply)("sessionId","status","value")
}

case class FindElementRequest(using: String, value: String)

object FindElementRequest {
  implicit def Encoder =
    jencode2L((o: FindElementRequest) => (o.using, o.value))("using", "value")
}

case class ElementResponse(sessionId: String, status: Int, value: Map[String, String])

object ElementResponse {
  implicit def Decoder =
    jdecode3L(ElementResponse.apply)("sessionId", "status", "value")
}

case class StringResponse(sessionId: String, status: Int, value: Option[String])

object StringResponse {
  implicit def Decoder =
    jdecode3L(StringResponse.apply)("sessionId", "status", "value")
}

case class BooleanResponse(sessionId: String, status: Int, value: Boolean)

object BooleanResponse {
  implicit def Decoder =
    jdecode3L(BooleanResponse.apply)("sessionId", "status", "value")
}

case class ElementClickRequest(id: String)

object ElementClickRequest {
  implicit def Encoder =
     jencode1L((o: ElementClickRequest) => o.id)("id")
}

case class ElementClearRequest(id: String, sessionId: String)

object ElementClearRequest {
  implicit def Encoder =
     jencode2L((o: ElementClearRequest) => (o.id, o.sessionId))("id", "sessionId")
}

