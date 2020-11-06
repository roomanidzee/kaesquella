package com.romanidze.kaesquella.core

import com.romanidze.kaesquella.core.models.ksql.Request
import tethys._
import tethys.jackson._
import tethys.JsonReader
import tethys.readers.ReaderError

package object models {

  def processBody[A: JsonReader](body: Either[String, String]): Either[ClientError, A] = {

    val errorMessage = "Error while processing response body"

    if (body.isLeft) {

      val errorBody: Either[ReaderError, ExecutionError] =
        body.swap.toOption.get.jsonAs[ExecutionError]

      if (errorBody.isLeft) {
        Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
      }

      Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))
    } else {

      val queryStatus: Either[ReaderError, A] = body.toOption.get.jsonAs[A]

      if (queryStatus.isLeft) {
        Left(ClientError(errorMessage, None, Some(queryStatus.swap.toOption.get.getMessage)))
      }

      Right(queryStatus.toOption.get)
    }

  }

}
