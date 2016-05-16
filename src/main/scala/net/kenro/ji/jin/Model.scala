package net.kenro.ji.jin

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

case class ChromeCapabilities(chromedriverVersion: String = "", userDataDir: String = "")

object ChromeCapabilities {
  implicit def ChromeOptionsJson =
    casecodec2(ChromeCapabilities.apply, ChromeCapabilities.unapply)("chromedriverVersion", "userDataDir")
}

case class BrowserStackCapabilities(name: Option[String] = None,
                                    browser: Option[String] = None,
                                    browserVersion: Option[String] = None,
                                    os: Option[String] = None,
                                    osVersion: Option[String] = None,
                                    resolution: Option[String] = None,
                                    browserStackSeleniumVersion: Option[String] = None,
                                    project: Option[String] = None,
                                    build: Option[String] = None,
                                    browserStackLocal: Option[Boolean] = None,
                                    browserStackIENoFlash: Option[Boolean] = None,
                                    browserStackIECompatibility: Option[Boolean] = None,
                                    browserStackIEDriver: Option[String] = None,
                                    browserStackIEEnablePopups: Option[Boolean] = None,
                                    browserStackSafariEnablePopups: Option[Boolean] = None,
                                    browserStackSafariAllowAllCookies: Option[Boolean] = None,
                                    browserStackSafariDriver: Option[String] = None,
                                    browserStackDebug: Option[Boolean] = None,
                                    browserStackVideo: Option[Boolean] = None,
                                    browserStackLocalIdentifier: Option[String] = None)

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
                        proxy: Option[Proxy] = None,
                        chrome: Option[ChromeCapabilities] = None,
                        browserStack: Option[BrowserStackCapabilities] = None)


object Capabilities {

  implicit def Encode =
    EncodeJson((o: Capabilities) =>
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
        ->: ("proxy" :?= o.proxy)
        ->?: ("chrome" :?= o.chrome)
        ->?: ("name" :?= o.browserStack.flatMap(_.name))
        ->?: ("browser" :?= o.browserStack.flatMap(_.browser))
        ->?: ("browserVersion" :?= o.browserStack.flatMap(_.browserVersion))
        ->?: ("os" :?= o.browserStack.flatMap(_.os))
        ->?: ("os_version" :?= o.browserStack.flatMap(_.osVersion))
        ->?: ("resolution" :?= o.browserStack.flatMap(_.resolution))
        ->?: ("browserstack.selenium_version" :?= o.browserStack.flatMap(_.browserStackSeleniumVersion))
        ->?: ("project" :?= o.browserStack.flatMap(_.project))
        ->?: ("build" :?= o.browserStack.flatMap(_.build))
        ->?: ("browserstack.local" :?= o.browserStack.flatMap(_.browserStackLocal))
        ->?: ("browserstack.ie.noFlash" :?= o.browserStack.flatMap(_.browserStackIENoFlash))
        ->?: ("browserstack.ie.compatibility" :?= o.browserStack.flatMap(_.browserStackIECompatibility))
        ->?: ("browserstack.ie.driver" :?= o.browserStack.flatMap(_.browserStackIEDriver))
        ->?: ("browserstack.ie.enablePopups" :?= o.browserStack.flatMap(_.browserStackIEEnablePopups))
        ->?: ("browserstack.safari.enablePopups" :?= o.browserStack.flatMap(_.browserStackSafariEnablePopups))
        ->?: ("browserstack.safari.allowAllCookies" :?= o.browserStack.flatMap(_.browserStackSafariAllowAllCookies))
        ->?: ("browserstack.safari.driver" :?= o.browserStack.flatMap(_.browserStackSafariDriver))
        ->?: ("browserstack.debug" :?= o.browserStack.flatMap(_.browserStackDebug))
        ->?: ("browserstack.video" :?= o.browserStack.flatMap(_.browserStackVideo))
        ->?: ("browserstack.localIdentifier" :?= o.browserStack.flatMap(_.browserStackLocalIdentifier))
        ->?: jEmptyObject)

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
      chrome <- (c --\ "proxy").as[Option[ChromeCapabilities]]
      name <- (c --\ "name").as[Option[String]]
      browser <- (c --\ "browser").as[Option[String]]
      browserVersion <- (c --\ "browserVersion").as[Option[String]]
      os <- (c --\ "os").as[Option[String]]
      osVersion <- (c --\ "osVersion").as[Option[String]]
      resolution <- (c --\ "resolution").as[Option[String]]
      project <- (c --\ "project").as[Option[String]]
      build <- (c --\ "build").as[Option[String]]
      browserStackSeleniumVersion <- (c --\ "browserstack.selenium.version").as[Option[String]]
      browserStackLocal <- (c --\ "browserstack.local").as[Option[Boolean]]
      browserStackIENoFlash <- (c --\ "browserstack.ie.noFlash").as[Option[Boolean]]
      browserStackIECompatibility <- (c --\ "browserstack.ie.compatibility").as[Option[Boolean]]
      browserStackIEDriver <- (c --\ "browserstack.ie.driver").as[Option[String]]
      browserStackIEEnablePopups <- (c --\ "browserstack.ie.enablePopups").as[Option[Boolean]]
      browserStackSafariEnablePopups <- (c --\ "browserstack.safari.enablePopups").as[Option[Boolean]]
      browserStackSafariAllowAllCookies <- (c --\ "browserstack.safari.allowAllCookies").as[Option[Boolean]]
      browserStackSafariDriver <- (c --\ "browserstack.safari.driver").as[Option[String]]
      browserStackDebug <- (c --\ "browserstack.debug").as[Option[Boolean]]
      browserStackVideo <- (c --\ "browserstack.video").as[Option[Boolean]]
      browserStackLocalId <- (c --\ "browserstack.localIdentifier").as[Option[String]]
    } yield Capabilities(browserName, platform, version, javascriptEnabled, takesScreenshot, handlesAlerts,
      databaseEnabled, locationContextEnabled, applicationCacheEnabled, browserConnectionEnabled, cssSelectorsEnabled,
      webStorageEnabled, rotatable, acceptSslCerts, nativeEvents, proxy, chrome, getBrowserStackCapabilities(
        name, browser, browserVersion, os, osVersion, resolution,
        project, build, browserStackSeleniumVersion, browserStackLocal, browserStackIENoFlash, browserStackIECompatibility,
        browserStackIEDriver, browserStackIEEnablePopups, browserStackSafariEnablePopups, browserStackSafariAllowAllCookies, browserStackSafariDriver,
        browserStackDebug, browserStackVideo, browserStackLocalId)
      ))


  private def getBrowserStackCapabilities(name: Option[String], browser: Option[String], browserVersion: Option[String], os: Option[String], osVersion: Option[String], resolution: Option[String],
                                          project: Option[String], build: Option[String], browserStackSeleniumVersion: Option[String], browserStackLocal: Option[Boolean], browserStackIENoFlash: Option[Boolean], browserStackIECompatibility: Option[Boolean],
                                          browserStackIEDriver: Option[String], browserStackIEEnablePopups: Option[Boolean], browserStackSafariEnablePopups: Option[Boolean], browserStackSafariAllowAllCookies: Option[Boolean], browserStackSafariDriver: Option[String],
                                          browserStackDebug: Option[Boolean], browserStackVideo: Option[Boolean], browserStackLocalId: Option[String]): Option[BrowserStackCapabilities] = {
    List(name, browser, browserVersion, os, osVersion, resolution,
      project, build, browserStackSeleniumVersion, browserStackLocal, browserStackIENoFlash, browserStackIECompatibility,
      browserStackIEDriver, browserStackIEEnablePopups, browserStackSafariEnablePopups, browserStackSafariAllowAllCookies, browserStackSafariDriver,
      browserStackDebug, browserStackVideo, browserStackLocalId).exists(i => i.isDefined) match {
      case false => None
      case  _ => Some(BrowserStackCapabilities(name, browser, browserVersion, os, osVersion, resolution,
          project, build, browserStackSeleniumVersion, browserStackLocal, browserStackIENoFlash, browserStackIECompatibility,
          browserStackIEDriver, browserStackIEEnablePopups, browserStackSafariEnablePopups, browserStackSafariAllowAllCookies, browserStackSafariDriver,
          browserStackDebug, browserStackVideo, browserStackLocalId))
    }
  }

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