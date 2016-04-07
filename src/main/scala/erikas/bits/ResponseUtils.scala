package erikas.bits

import io.shaka.http.Response
import argonaut.Argonaut._
import argonaut.DecodeJson


object ResponseUtils {

  implicit class ResponseDecoder(val response: Response) {
    def decode[X: DecodeJson]: X = {
      response.entityAsString.decodeEither[X] match {
        case Left(message)          => throw APIResponseError(message)
        case Right(decodedResponse) => decodedResponse
      }
    }
  }
}
