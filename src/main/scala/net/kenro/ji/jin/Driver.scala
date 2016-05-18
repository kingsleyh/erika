package net.kenro.ji.jin

import argonaut._
import io.shaka.http.ContentType.APPLICATION_JSON
import io.shaka.http.Http.http
import io.shaka.http.Request.{DELETE, GET, POST}
import io.shaka.http.Status.OK
import io.shaka.http.{Entity, Response}

import scala.collection.mutable.Queue

case class APIResponseError(message: String) extends Exception(message)

trait BaseDriver {
  def doGet(url: String): Response

  def doPost(url: String, json: Json): Response

  def doDelete(url: String): Response
}

class Driver(host: String, port: Int, context: String = "", transport: String = "http://") extends BaseDriver {

  val phantomServer = s"$transport$host:$port$context"

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

case class BasicAuth(username: String = "", password: String = "") {
  def toOption = Option(this)
}

class UrlDriver(remoteUrl: String, basicAuth: Option[BasicAuth]) extends BaseDriver {

  override def doGet(url: String): Response = {
    basicAuth match {
      case Some(BasicAuth(username, password)) => http(GET(remoteUrl + url).contentType(APPLICATION_JSON).basicAuth(username, password))
      case _ => http(GET(remoteUrl + url).contentType(APPLICATION_JSON))
    }
  }

  override def doPost(url: String, json: Json) = {
    basicAuth match {
      case Some(BasicAuth(username, password)) => http(POST(remoteUrl + url).contentType(APPLICATION_JSON).basicAuth(username, password).entity(json.toString()))
      case _ => http(POST(remoteUrl + url).contentType(APPLICATION_JSON).entity(json.toString()))
    }
  }

  override def doDelete(url: String) = {
    basicAuth match {
      case Some(BasicAuth(username, password)) => http(DELETE(remoteUrl + url).contentType(APPLICATION_JSON).basicAuth(username, password))
      case _ => http(DELETE(remoteUrl + url).contentType(APPLICATION_JSON))
    }

  }
}


object Driver {

  def apply(host: String, port: Int): Driver = new Driver(host, port)

  def apply(host: String, port: Int, context: String, transport: String): Driver = new Driver(host, port, context, transport)

  def apply(url: String, basicAuth: Option[BasicAuth] = None) = new UrlDriver(url, basicAuth)

  def handleRequest(url: String, response: Response): Response = {
    response match {
      case r@Response(OK, _, _) => r
      case _ => throw APIResponseError(s"request for $url returned failed error code: ${response.status.code} with message: ${response.entityAsString}")
    }
  }
}

case class QueueEmptyError(message: String) extends Exception(message)
case class NoCannedResponseError(message: String) extends Exception(message)

class TestDriver(host: String, port: Int) extends BaseDriver {

  private var postResponse: Option[Response] = None
  private var getResponse: Option[Response] = None
  private var deleteResponse: Option[Response] = None

  private var requestUrl = "url-was-not-specified"

  private var postRequest = Json()

  private var getResponses = new Queue[Response]()
  private var postResponses = new Queue[Response]()

  override def doGet(url: String): Response = {
    requestUrl = url
    getResponse.foreach(r => getResponses += r)
    if(getResponses.isEmpty){
     throw QueueEmptyError("Error: The response queue was empty")
    }
    getResponses.dequeue()
  }

  override def doPost(url: String, json: Json): Response = {
    requestUrl = url
    postRequest = json
    postResponse.foreach(r => postResponses += r)
    if(postResponses.isEmpty){
      throw QueueEmptyError("Error: The response queue was empty")
    }
    postResponses.dequeue()
  }

  override def doDelete(url: String): Response = {
    requestUrl = url
    deleteResponse.getOrElse(throw NoCannedResponseError(s"No canned delete response for: $url"))
  }

  def withPostResponse(cannedResponse: String, action: () => Unit): TestDriver = {
    postResponse = Some(Response(OK, entity = Some(Entity(cannedResponse))))
    action()
    this
  }

  def withPostResponseAction[T](cannedResponse: String, action: () => T): T = {
    postResponse = Some(Response(OK, entity = Some(Entity(cannedResponse))))
    action()
  }

  def getRequestUrl = requestUrl

  def getPostRequest = postRequest.nospaces

  def withGetResponse(cannedResponse: String, action: () => Unit): TestDriver = {
    getResponse = Some(Response(OK, entity = Some(Entity(cannedResponse))))
    action()
    this
  }

  def withGetResponses(cannedResponses: List[String], action: () => Unit = () => {}): TestDriver = {
    cannedResponses.foreach(cr => getResponses += Response(OK, entity = Some(Entity(cr))))
    action()
    this
  }

  def withPostResponses(cannedResponses: List[String], action: () => Unit = () => {}): TestDriver = {
    cannedResponses.foreach(cr => postResponses += Response(OK, entity = Some(Entity(cr))))
    action()
    this
  }

  def withGetResponseAction[T](cannedResponse: String, action: () => T): T = {
    getResponse = Some(Response(OK, entity = Some(Entity(cannedResponse))))
    action()
  }

  def withDeleteResponse(cannedResponse: String, action: () => Unit): TestDriver = {
    deleteResponse = Some(Response(OK, entity = Some(Entity(cannedResponse))))
    action()
    this
  }

}

object TestDriver {

  def apply(host: String, port: Int): TestDriver = new TestDriver(host, port)

  def handleFailedRequest(url: String, response: Response) = Driver.handleRequest(url, response)

}
