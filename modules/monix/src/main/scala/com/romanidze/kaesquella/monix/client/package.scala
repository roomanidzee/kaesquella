package com.romanidze.kaesquella.monix

import java.nio.ByteBuffer

import com.romanidze.kaesquella.core.client.Output
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import monix.eval.Task
import monix.reactive.Observable
import sttp.client.SttpBackend
import sttp.client.okhttp.WebSocketHandler

package object client {
  type MonixBackend = SttpBackend[Task, Observable[ByteBuffer], WebSocketHandler]
  type RowInfoResponse = Task[Output[Observable[Output[RowInfo]]]]
}
