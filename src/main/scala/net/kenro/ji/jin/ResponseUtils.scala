package net.kenro.ji.jin

import argonaut.Argonaut._
import argonaut.DecodeJson
import io.shaka.http.Response


object ResponseUtils {

  implicit class ResponseDecoder(val response: Response) {
    def decode[X: DecodeJson]: X = {
      response.entityAsString.decodeEither[X] match {
        case Left(message) => throw APIResponseError(s"\n\nThe response could not be decoded due to\n: $message - \nwith response code: ${response.status.code} \nand message: ${response.entityAsString}\n\n")
        case Right(decodedResponse) => decodedResponse
      }
    }
  }

  implicit class StringDecoder(val jsonString: String) {
    def decode[X: DecodeJson]: X = {
      jsonString.decodeEither[X] match {
        case Left(message) => throw APIResponseError(s"\n\nThe json string could not be decoded due to\n: $message")
        case Right(decodedString) => decodedString
      }
    }

  }

}






