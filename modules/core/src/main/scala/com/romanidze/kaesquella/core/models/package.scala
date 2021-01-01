package com.romanidze.kaesquella.core

import tethys._
import tethys.jackson._
import tethys.JsonReader
import tethys.readers.ReaderError

package object models {

  val errorMessage = "Error while processing response body"

  def processLeft[A: JsonReader](body: String): Left[ClientError, Nothing] = {

    val errorBody: Either[ReaderError, ExecutionError] = body.jsonAs[ExecutionError]

    errorBody match {
      case Left(value)  => Left(ClientError(errorMessage, None, Some(value.getMessage)))
      case Right(value) => Left(ClientError(errorMessage, Some(value), None))
    }

  }

  def processRight[A: JsonReader](body: String): Either[ClientError, A] = {

    val queryStatus: Either[ReaderError, A] = body.jsonAs[A]

    queryStatus match {
      case Left(value)  => Left(ClientError(errorMessage, None, Some(value.getMessage)))
      case Right(value) => Right(value)
    }

  }

  def processBody[A: JsonReader](body: Either[String, String]): Either[ClientError, A] = {

    body match {
      case Left(value)  => processLeft(value)
      case Right(value) => processRight(value)
    }

  }

}
