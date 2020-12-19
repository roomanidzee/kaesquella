package com.romanidze.kaesquella.core.client

import sttp.client._

import java.nio.ByteBuffer

import scala.concurrent.duration.Duration

abstract class ClientFPInterpreter[F[_], G[_], WSBackend[_]](baseURL: String) {

  implicit val backend: SttpBackend[F, G[ByteBuffer], WSBackend]

  type SimpleRequest = Request[Either[String, String], Nothing]
  type SimpleResponse = F[Response[Either[String, String]]]
  type StreamingRequest = RequestT[Identity, Either[String, G[ByteBuffer]], G[ByteBuffer]]
  type StreamingResponse = F[Response[Either[String, G[ByteBuffer]]]]

  val ksqlHeader: String = "application/vnd.ksql.v1+json"

  def sendGETRequest(requestURL: String): SimpleResponse = {

    val request: SimpleRequest = basicRequest
      .header("Accept", ksqlHeader)
      .header("Content-Type", ksqlHeader)
      .get(uri"${requestURL}")

    request.send()

  }

  def sendPOSTRequest(requestURL: String, inputBody: String): SimpleResponse = {

    val request: SimpleRequest = basicRequest
      .header("Accept", ksqlHeader)
      .header("Content-Type", ksqlHeader)
      .post(uri"${requestURL}")
      .body(inputBody)

    request.send()

  }

  def sendStreamRequest(requestURL: String, inputBody: String): StreamingResponse = {

    val request: StreamingRequest = basicRequest
      .header("Accept", ksqlHeader)
      .header("Content-Type", ksqlHeader)
      .post(uri"${requestURL}")
      .body(inputBody)
      .response(asStream[G[ByteBuffer]])
      .readTimeout(Duration.Inf)

    request.send()

  }

}
