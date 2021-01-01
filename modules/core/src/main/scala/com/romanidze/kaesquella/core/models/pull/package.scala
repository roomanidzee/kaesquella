package com.romanidze.kaesquella.core.models

import org.json4s.JsonAST.JArray
import tethys._
import tethys.jackson._
import tethys.readers.ReaderError
import org.json4s._
import org.json4s.jackson.JsonMethods._

package object pull {

  val errorMessage = "Could not deserialize pull response body"

  def processData(body: String): Either[ClientError, PullResponse] = {

    parseOpt(body) match {
      case None => Left(ClientError(errorMessage, None, None))
      case Some(value) =>
        Right(PullResponse(isSchema = false, None, Some(value.asInstanceOf[JArray])))
    }

  }

  def convert(body: String): Either[ClientError, PullResponse] = {

    val schemaAttempt: Either[ReaderError, ResponseSchema] = body.jsonAs[ResponseSchema]

    schemaAttempt match {
      case Left(_)      => processData(body)
      case Right(value) => Right(PullResponse(isSchema = true, Some(value), None))
    }

  }

}
