package com.romanidze.kaesquella.monix

import java.nio.ByteBuffer

import com.romanidze.kaesquella.core.models.{ClientError, ExecutionError}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import monix.execution.atomic.AtomicAny
import monix.reactive.Observable
import monix.reactive.observers.Subscriber
import monix.nio.text.UTF8Codec._
import tethys._
import tethys.jackson._
import tethys.readers.ReaderError

/**
 * Utils package with some common things
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @version 0.0.1
 */
package object utils {
  type AtomicSubscriber = AtomicAny[Option[Subscriber[Array[Byte]]]]

  def processRowInfo(
    body: Either[String, Observable[ByteBuffer]]
  ): Either[ClientError, Observable[Either[ClientError, RowInfo]]] = {

    val errorMessage = "Error while processing response body"

    if (body.isLeft) {

      val errorBody: Either[ReaderError, ExecutionError] =
        body.swap.toOption.get.jsonAs[ExecutionError]

      if (errorBody.isLeft) {
        Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
      }

      Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))
    } else {

      val observableBody: Observable[ByteBuffer] = body.toOption.get
      val lineTerm = Array('\n'.toByte)

      val readResult: Observable[Either[ClientError, RowInfo]] = observableBody.map { elem =>
        val byteArr: Array[Byte] = new Array[Byte](elem.remaining())
        elem.get(byteArr)
        elem.rewind()

        byteArr

      }
        .pipeThrough(Framing(lineTerm, 200))
        .pipeThrough(utf8Decode)
        .map(elem => elem.jsonAs[RowInfo])
        .map { elem =>
          if (elem.isLeft) {
            Left(ClientError(errorMessage, None, Some(elem.swap.toOption.get.getMessage)))
          } else {
            Right(elem.toOption.get)
          }

        }

      Right(readResult)

    }

  }

}
