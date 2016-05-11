package erikas.bits

import io.shaka.http.Response
import argonaut.Argonaut._
import argonaut.DecodeJson

import scalaz.{\/-, -\/}


object ResponseUtils {

  implicit class ResponseDecoder(val response: Response) {
    def decode[X: DecodeJson]: X = {
      response.entityAsString.decodeEither[X] match {
        case -\/(message)          => throw APIResponseError(s"\n\nThe response could not be decoded due to\n: $message - \nwith response code: ${response.status.code} \nand message: ${response.entityAsString}\n\n")
        case \/-(decodedResponse) => decodedResponse
      }
    }
  }
}






