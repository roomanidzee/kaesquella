package com.romanidze.kaesquella.core

import tethys._
import tethys.jackson._
import tethys.JsonReader
import tethys.readers.ReaderError

package object models {

  val errorMessage = "Error while processing response body"

  def processLeft[A: JsonReader](body: String): Left[ClientError, Nothing] = {

    val errorBody: Either[ReaderError, ExecutionError] = body.jsonAs[ExecutionError]

    if (errorBody.isLeft) {
      Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
    }

    Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))

  }

  def processRight[A: JsonReader](body: String): Right[Nothing, A] = {

    val queryStatus: Either[ReaderError, A] = body.jsonAs[A]

    if (queryStatus.isLeft) {
      Left(ClientError(errorMessage, None, Some(queryStatus.swap.toOption.get.getMessage)))
    }

    Right(queryStatus.toOption.get)

  }

  def processBody[A: JsonReader](body: Either[String, String]): Either[ClientError, A] = {

    body match {
      case Left(value)  => processLeft(value)
      case Right(value) => processRight(value)
    }

  }

}
