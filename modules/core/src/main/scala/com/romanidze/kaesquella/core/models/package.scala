package com.romanidze.kaesquella.core

import com.romanidze.kaesquella.core.models.ksql.Request
import tethys._
import tethys.jackson._
import tethys.JsonReader
import tethys.readers.ReaderError

package object models {

  private def processLeft[A: JsonReader](body: String, errorMessage: String) = {

    val errorBody: Either[ReaderError, ExecutionError] = body.jsonAs[ExecutionError]

    if (errorBody.isLeft) {
      Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
    }

    Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))

  }

  private def processRight[A: JsonReader](body: String, errorMessage: String) = {

    val queryStatus: Either[ReaderError, A] = body.jsonAs[A]

    if (queryStatus.isLeft) {
      Left(ClientError(errorMessage, None, Some(queryStatus.swap.toOption.get.getMessage)))
    }

    Right(queryStatus.toOption.get)

  }

  def processBody[A: JsonReader](body: Either[String, String]): Either[ClientError, A] = {

    val errorMessage = "Error while processing response body"

    body match {
      case Left(value)  => processLeft(value, errorMessage)
      case Right(value) => processRight(value, errorMessage)
    }

  }

}
