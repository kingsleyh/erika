package net.kenro.ji.jin

class SessionHelper {

  private val testDriver = TestDriver("some-host",1234)
  private val session = new Session(testDriver)

  def sessionWithDriver() = (session, testDriver)

}

object SessionHelper {
  def apply() = new SessionHelper().sessionWithDriver()
}