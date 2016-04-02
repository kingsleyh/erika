package erikas.bits

import argonaut._
import io.shaka.http.ContentType.APPLICATION_JSON
import io.shaka.http.Http.http
import io.shaka.http.Request.{DELETE, GET, POST}
import io.shaka.http.Response
import io.shaka.http.Status.OK

case class APIResponseError(message: String) extends Exception(message)

class Driver(host: String, port: Int) {

  val phantomServer = s"http://$host:$port"

  def goGet(url: String): Response = {
    http(GET(phantomServer + url).contentType(APPLICATION_JSON))
  }

  def doPost(url: String, json: Json) = {
    http(POST(phantomServer + url).contentType(APPLICATION_JSON).entity(json.toString()))
  }

  def doDelete(url: String) = {
    http(DELETE(phantomServer + url).contentType(APPLICATION_JSON))
  }

}

object Driver {
  def handleFailedRequest(url: String, response: Response) = {
    if(response.status != OK)
      throw APIResponseError(s"request for $url returned failed error code: ${response.status.code} with message: ${response.entityAsString}")
  }
}
