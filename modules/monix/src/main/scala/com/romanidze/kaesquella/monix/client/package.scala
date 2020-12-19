package com.romanidze.kaesquella.monix

import java.nio.ByteBuffer

import com.romanidze.kaesquella.core.models.ClientError
import com.romanidze.kaesquella.core.client.Output
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import monix.eval.Task
import monix.reactive.Observable
import sttp.client.{Identity, Request, RequestT, Response, SttpBackend}
import sttp.client.asynchttpclient.WebSocketHandler

package object client {
  type MonixBackend = SttpBackend[Task, Observable[ByteBuffer], WebSocketHandler]

  type ClientRequest = Request[Either[String, String], Nothing]
  type ClientSimpleResponse = Task[Response[Either[String, String]]]

  type ClientStreamingRequest =
    RequestT[Identity, Either[String, Observable[ByteBuffer]], Observable[ByteBuffer]]
  type ClientStreamingResponse = Task[Response[Either[String, Observable[ByteBuffer]]]]
  type RowInfoResponse = Task[Output[Observable[Output[RowInfo]]]]
}
