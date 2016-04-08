package erikas.bits

import io.shaka.http.Response
import argonaut.Argonaut._
import argonaut.DecodeJson


object ResponseUtils {

  implicit class ResponseDecoder(val response: Response) {
    def decode[X: DecodeJson]: X = {
      response.entityAsString.decodeEither[X] match {
        case Left(message)          => throw APIResponseError(s"\n\nThe response could not be decoded due to\n: $message - \nwith response code: ${response.status.code} \nand message: ${response.entityAsString}\n\n")
        case Right(decodedResponse) => decodedResponse
      }
    }
  }
}
