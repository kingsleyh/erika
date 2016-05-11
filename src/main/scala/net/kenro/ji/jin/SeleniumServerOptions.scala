package net.kenro.ji.jin

class SeleniumServerOptions {

  private var timeout = ""
  private var interactive = ""
  private var multiWindow = ""
  private var forcedBrowserMode = ""
  private var userExtensions = ""
  private var browserSessionReuse = ""
  private var avoidProxy = ""
  private var firefoxProfileTemplate = ""
  private var debug = ""
  private var log = ""
  private var htmlSuite = ""
  private var proxyInjectionMode = ""
  private var dontInjectRegex = ""
  private var userJsInjection = ""
  private var userContentTransformation = ""

  def setTimeout(value: String) = {
    timeout = s"-timeout=$value"
    this
  }

  def setInteractive(value: String) = {
    interactive = s"-interactive=$value"
    this
  }

  def getOptions: String = {
    List(timeout, interactive).filterNot(o => o.isEmpty).mkString(" ")
  }

}

object SeleniumServerOptions {
  def apply() = new SeleniumServerOptions()
}
