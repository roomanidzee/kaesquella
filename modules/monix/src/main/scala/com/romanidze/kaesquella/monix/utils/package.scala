package com.romanidze.kaesquella.monix

import java.nio.ByteBuffer

import com.romanidze.kaesquella.core.models.pull.{convert, PullResponse}
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
  val chunkSize: Int = 200
  val lineTerm: Array[Byte] = Array('\n'.toByte)

  def processLeft(body: String): Left[ClientError, Nothing] = {

    val errorMessage = "Error while working with stream of response body"

    val errorBody: Either[ReaderError, ExecutionError] = body.jsonAs[ExecutionError]

    if (errorBody.isLeft) {
      Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
    }

    Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))

  }

  def prepareObservable(body: Observable[ByteBuffer]): Observable[String] = {

    body.map { elem =>
      val byteArr: Array[Byte] = new Array[Byte](elem.remaining())
      elem.get(byteArr)
      elem.rewind()

      byteArr

    }
      .pipeThrough(Framing(lineTerm, chunkSize))
      .pipeThrough(utf8Decode)

  }

  def processRowInfo(
    body: Either[String, Observable[ByteBuffer]]
  ): Either[ClientError, Observable[Either[ClientError, RowInfo]]] = {

    val errorMessage = "Error while processing response body for row information"

    body match {
      case Left(value) => processLeft(value)
      case Right(value) => {

        val readResult: Observable[Either[ClientError, RowInfo]] =
          prepareObservable(value)
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

  def processPullQuery(
    body: Either[String, Observable[ByteBuffer]]
  ): Either[ClientError, Observable[Either[ClientError, PullResponse]]] = {

    body match {

      case Left(value) => processLeft(value)
      case Right(value) => {

        val readResult =
          prepareObservable(value)
            .map(elem => convert(elem))

        Right(readResult)

      }

    }

  }

}
