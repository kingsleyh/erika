package net.kenro.ji.jin

class ChromeOptions {

  private var adbPort = ""
  private var logPath = ""
  private var verbose = ""
  private var silent = ""
  private var urlBase = ""
  private var whitelistedIps = ""
  private var portServer = ""

  def setAdbPort(value: String) = {
    adbPort = s"--adbPort=$value"
    this
  }

  def setLogPath(value: String) = {
    logPath = s"--logPath=$value"
    this
  }

  def setVerbose(value: Boolean) = {
    if(value) verbose = "--verbose"
    this
  }

  def setSilent(value: Boolean) = {
    if(value) silent = "--silent"
    this
  }

  def setUrlBase(value: String) = {
    urlBase = s"--url-base=$value"
    this
  }

  def setWhitelistedIps(value: String) = {
    whitelistedIps = s"--whitelisted-ips=$value"
    this
  }

  def setPortServer(value: String) = {
    portServer = s"--port-server=$value"
    this
  }

  def getOptions: String = {
    List(adbPort, logPath, verbose, silent, urlBase, whitelistedIps, portServer).filterNot(o => o.isEmpty).mkString(" ")
  }

}

object ChromeOptions {
  def apply() = new ChromeOptions()
}
