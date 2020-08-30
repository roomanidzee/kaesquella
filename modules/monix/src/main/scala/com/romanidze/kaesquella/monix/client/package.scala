package com.romanidze.kaesquella.monix

import java.nio.ByteBuffer

import monix.eval.Task
import monix.reactive.Observable
import sttp.client.{Request, Response, SttpBackend}
import sttp.client.asynchttpclient.WebSocketHandler

package object client {
  type MonixBackend = SttpBackend[Task, Observable[ByteBuffer], WebSocketHandler]
  type ClientRequest = Request[Either[String, String], Nothing]
  type ClientSimpleResponse = Task[Response[Either[String, String]]]
}
