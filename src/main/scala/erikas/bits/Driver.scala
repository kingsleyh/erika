package erikas.bits

import argonaut._
import io.shaka.http.ContentType.APPLICATION_JSON
import io.shaka.http.Http.http
import io.shaka.http.Request.{DELETE, GET, POST}
import io.shaka.http.{Entity, Response, Status}
import io.shaka.http.Status.OK

case class APIResponseError(message: String) extends Exception(message)

trait PhantomDriver {
  def doGet(url: String): Response

  def doPost(url: String, json: Json): Response

  def doDelete(url: String): Response
}

class Driver(host: String, port: Int) extends PhantomDriver {

  val phantomServer = s"http://$host:$port"

  override def doGet(url: String): Response = {
    http(GET(phantomServer + url).contentType(APPLICATION_JSON))
  }

  override def doPost(url: String, json: Json) = {
    http(POST(phantomServer + url).contentType(APPLICATION_JSON).entity(json.toString()))
  }

  override def doDelete(url: String) = {
    http(DELETE(phantomServer + url).contentType(APPLICATION_JSON))
  }

}

object Driver {

  def apply(host: String, port: Int): Driver = new Driver(host, port)

  def handleRequest(url: String, response: Response) = {
    response match {
      case r@Response(OK, _, _) => r
      case _ => throw APIResponseError(s"request for $url returned failed error code: ${response.status.code} with message: ${response.entityAsString}")
    }
  }
}

class TestDriver(host: String, port: Int) extends PhantomDriver {

  private var postResponse = Response(OK, entity = Some(Entity("hello")))
  private var getResponse = Response(OK, entity = Some(Entity("hello")))
  private val deleteResponse = Response(OK, entity = Some(Entity("hello")))

  private var postRequest = Json()

  override def doGet(url: String): Response = getResponse

  override def doPost(url: String, json: Json): Response = {
    postRequest = json
    postResponse
  }

  override def doDelete(url: String): Response = deleteResponse

  def withPostResponse(cannedResponse: String, action: () => Unit): TestDriver = {
    postResponse = Response(OK, entity = Some(Entity(cannedResponse)))
    action()
    this
  }

  def withPostResponseAction[T](cannedResponse: String, action: () => T): T = {
    postResponse = Response(OK, entity = Some(Entity(cannedResponse)))
    action()
  }

  def getPostRequest = postRequest.nospaces

  def withGetResponse(cannedResponse: String, action: () => Unit): TestDriver = {
    getResponse = Response(OK, entity = Some(Entity(cannedResponse)))
    action()
    this
  }

  def withGetResponseAction[T](cannedResponse: String, action: () => T): T = {
    getResponse = Response(OK, entity = Some(Entity(cannedResponse)))
    action()
  }

}

object TestDriver {

  def apply(host: String, port: Int): TestDriver = new TestDriver(host, port)

  def handleFailedRequest(url: String, response: Response) = Driver.handleRequest(url, response)

}
