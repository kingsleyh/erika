package net.kenro.ji.jin

object SSLProtocol extends Enumeration {
  val SSLV3 = Value("sslv3")
  val SSLV2 = Value("sslv2")
  val TLSV1 = Value("tlsv1")
  val ANY = Value("any")

}

class PhantomJsOptions {

  private var cookiesFiles = ""
  private var diskCache = ""
  private var loadImages = ""
  private var ignoreSslErrors = ""
  private var webSecurity = ""
  private var sslProtocol = ""

  def setCookiesFiles(pathToCookiesFile: String) = {
    cookiesFiles = s"--cookies-file=$pathToCookiesFile"
    this
  }

  def setDiskCache(value: Boolean) = {
    diskCache = s"--disk-cache=$value"
    this
  }

  def setLoadImage(value: Boolean) = {
    loadImages = s"--load-images=$value"
    this
  }

  def setIgnoreSslError(value: Boolean) = {
    ignoreSslErrors = s"--ignore-ssl-errors=$value"
    this
  }

  def setWebSecurity(value: Boolean) = {
    webSecurity = s"--web-security=$value"
    this
  }

  def setSslProtocol(protocol: SSLProtocol.Value) = {
    sslProtocol = s"--ssl-protocol=$protocol"
    this
  }

  def getOptions: String = {
    List(ignoreSslErrors, diskCache, loadImages, webSecurity, sslProtocol).filterNot(o => o.isEmpty).mkString(" ")
  }

}

object PhantomJsOptions {
  def apply() = new PhantomJsOptions()
}
