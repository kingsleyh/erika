package erikas.bits

import argonaut.Argonaut._
import argonaut.{DecodeJson, EncodeJson, Json}

case class Proxy(proxyType: String = ProxyType.DIRECT,
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

sealed abstract class Enum(val value: String){
  def apply() = value
}

object Platform {
  def WINDOWS = "WINDOWS"
  def XP = "XP"
  def VISTA = "VISTA"
  def MAC = "MAC"
  def LINUX = "LINUX"
  def UNIX = "UNIX"
}


object ProxyType {
  def DIRECT = "direct"
  def MANUAL = "manual"
  def PAC = "pac"
  def AUTODETECT = "autodetect"
  def SYSTEM = "system"
}

case class Capabilities(browserName: String = "phantomjs",
                        platform: String = Platform.MAC,
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
                        proxy: Option[Proxy] = None)

object Capabilities {
  implicit def CapabilitiesJson =
    casecodec16(Capabilities.apply, Capabilities.unapply)("browserName", "platform", "version", "javascriptEnabled", "takesScreenshot", "handlesAlerts",
      "databaseEnabled", "locationContextEnabled", "applicationCacheEnabled", "browserConnectionEnabled", "cssSelectorsEnabled", "webStorageEnabled",
      "rotatable", "acceptSslCerts", "nativeEvents", "proxy")
}

case class ChromeCapabilities(browserName: String = "chrome",
                              platform: String = Platform.MAC,
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
                              proxy: Option[Proxy] = None,
                              args: Option[String] = None,
                              binary: Option[String] = None,
                              extensions: Option[String] = None,
                              localState: Option[String] = None,
                              prefs: Option[Map[String,String]] = None,
                              detach: Boolean = false,
                              debuggerAddress: Option[String] = None,
                              excludeSwitches: Option[List[String]] = None,
                              minidumpPath: Option[String] = None,
                              mobileEmulation: Option[Map[String,String]] = None,
                              perfLoggingPrefs: Option[Map[String,String]] = None,
                              windowTypes: Option[String] = None)

object ChromeCapabilities {

  implicit def Encode =
    EncodeJson((o: ChromeCapabilities) =>
      ("browserName" := o.browserName)
      ->: ("platform" := o.platform)
      ->: ("version" := o.version)
      ->: ("javascriptEnabled" := o.javascriptEnabled)
      ->: ("takesScreenshot" := o.takesScreenshot)
      ->: ("handlesAlerts" := o.handlesAlerts)
      ->: ("databaseEnabled" := o.databaseEnabled)
      ->: ("locationContextEnabled" := o.locationContextEnabled)
      ->: ("applicationCacheEnabled" := o.applicationCacheEnabled)
      ->: ("browserConnectionEnabled" := o.browserConnectionEnabled)
      ->: ("cssSelectorsEnabled" := o.cssSelectorsEnabled)
      ->: ("webStorageEnabled" := o.webStorageEnabled)
      ->: ("rotatable" := o.rotatable)
      ->: ("acceptSslCerts" := o.acceptSslCerts)
      ->: ("nativeEvents" := o.nativeEvents)
      ->: ("proxy" := o.proxy)
      ->: ("args" := o.args)
      ->: ("binary" := o.binary)
      ->: ("extensions" := o.extensions)
      ->: ("localState" := o.localState)
      ->: ("prefs" := o.prefs)
      ->: ("detach" := o.detach)
      ->: ("debuggerAddress" := o.debuggerAddress)
      ->: ("excludeSwitches" := o.excludeSwitches)
      ->: ("minidumpPath" := o.minidumpPath)
      ->: ("mobileEmulation" := o.mobileEmulation)
      ->: ("perfLoggingPrefs" := o.perfLoggingPrefs)
      ->: ("windowTypes" := o.windowTypes)
      ->: jEmptyObject)

  implicit def Decode =
    DecodeJson(c => for {
      browserName <- (c --\ "browserName").as[String]
      platform <- (c --\ "platform").as[String]
      version <- (c --\ "version").as[String]
      javascriptEnabled <- (c --\ "javascriptEnabled").as[Boolean]
      takesScreenshot <- (c --\ "takesScreenshot").as[Boolean]
      handlesAlerts <- (c --\ "handlesAlerts").as[Boolean]
      databaseEnabled <- (c --\ "databaseEnabled").as[Boolean]
      locationContextEnabled <- (c --\ "locationContextEnabled").as[Boolean]
      applicationCacheEnabled <- (c --\ "applicationCacheEnabled").as[Boolean]
      browserConnectionEnabled <- (c --\ "browserConnectionEnabled").as[Boolean]
      cssSelectorsEnabled <- (c --\ "cssSelectorsEnabled").as[Boolean]
      webStorageEnabled <- (c --\ "webStorageEnabled").as[Boolean]
      rotatable <- (c --\ "rotatable").as[Boolean]
      acceptSslCerts <- (c --\ "acceptSslCerts").as[Boolean]
      nativeEvents <- (c --\ "nativeEvents").as[Boolean]
      proxy <- (c --\ "proxy").as[Option[Proxy]]
      args <- (c --\ "args").as[Option[String]]
      binary <- (c --\ "binary").as[Option[String]]
      extensions <- (c --\ "extensions").as[Option[String]]
      localState <- (c --\ "localState").as[Option[String]]
      prefs <- (c --\ "prefs").as[Option[Map[String,String]]]
      detach <- (c --\ "detach").as[Boolean]
      debuggerAddress <- (c --\ "debuggerAddress").as[Option[String]]
      excludeSwitches <- (c --\ "excludeSwitches").as[Option[List[String]]]
      minidumpPath <- (c --\ "minidumpPath").as[Option[String]]
      mobileEmulation <- (c --\ "mobileEmulation").as[Option[Map[String,String]]]
      perfLoggingPrefs <- (c --\ "perfLoggingPrefs").as[Option[Map[String,String]]]
      windowTypes <- (c --\ "windowTypes").as[Option[String]]
    } yield ChromeCapabilities(browserName, platform, version, javascriptEnabled, takesScreenshot, handlesAlerts,
      databaseEnabled, locationContextEnabled, applicationCacheEnabled, browserConnectionEnabled, cssSelectorsEnabled,
      webStorageEnabled, rotatable, acceptSslCerts, nativeEvents, proxy, args, binary, extensions, localState, prefs,
      detach, debuggerAddress, excludeSwitches, minidumpPath, mobileEmulation, perfLoggingPrefs, windowTypes))
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

case class ElementResponses(sessionId: String, status: Int, value: List[Map[String, String]])

object ElementResponses {
  implicit def Decoder =
    jdecode3L(ElementResponses.apply)("sessionId", "status", "value")
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

case class ServerStatus(build: Map[String, String], os: Map[String, String])

object ServerStatus {
  implicit def Decoder = jdecode2L(ServerStatus.apply)("build","os")
}

case class ServerStatusResponse(sessionId: Option[String], status: Int, value: ServerStatus)

object ServerStatusResponse {
  implicit def Decoder = jdecode3L(ServerStatusResponse.apply)("sessionId", "status" ,"value")
}

case class CapabilityResponse(sessionId: Option[String], status: Int, value: Capabilities)

object CapabilityResponse {
  implicit def Decoder = jdecode3L(CapabilityResponse.apply)("sessionId", "status", "value")
}

case class ChromeCapabilityResponse(sessionId: Option[String], status: Int, value: ChromeCapabilities)

object ChromeCapabilityResponse {
  implicit def Decoder = jdecode3L(ChromeCapabilityResponse.apply)("sessionId", "status", "value")
}

case class WindowHandle(handleId: String)

object WindowHandle {
  implicit def Decoder = jdecode1L(WindowHandle.apply)("handleId")
}

case class WindowHandleResponse(sessionId: String, status: Int, value: String)

object WindowHandleResponse {
  implicit def Decoder = jdecode3L(WindowHandleResponse.apply)("sessionId", "status", "value")
}

case class WindowHandlesResponse(sessionId: String, status: Int, value: List[String])

object WindowHandlesResponse {
  implicit def Decoder = jdecode3L(WindowHandlesResponse.apply)("sessionId", "status", "value")
}

case class ExecuteScriptRequest(script: String, args: List[String])

object ExecuteScriptRequest {
  implicit def Encoder = jencode2L((o: ExecuteScriptRequest) => (o.script, o.args))("script","args")
}

object TimeoutType extends Enumeration {
  val SCRIPT = Value("script")
  val IMPLICIT = Value("implicit")
  val PAGE_LOAD = Value("page load")

}

case class TimeoutReq(timeoutType: String, ms: Int)

object TimeoutReq {
  implicit def Encoder = jencode2L((o: TimeoutReq) => (o.timeoutType, o.ms))("type", "ms")
}

case class TimeoutRequest(timeoutType: TimeoutType.Value, milliseconds: Int) {
  def toJson() = {
    TimeoutReq(timeoutType.toString, milliseconds).asJson
  }
}

case class TimeoutValueRequest(milliseconds: Int)

object TimeoutValueRequest {
  implicit def Encoder = jencode1L((o: TimeoutValueRequest) => o.milliseconds)("ms")
}

case class SendKeysRequest(value: List[Char])

object SendKeysRequest {
  implicit def Encoder = jencode1L((o: SendKeysRequest) => o.value)("value")
}