package com.romanidze.kaesquella.core.models.ksql.stream

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class StreamResponse(statement: String, streams: Seq[StreamInfo]) extends KSQLResponse

object StreamResponse {

  implicit val reader: JsonReader[StreamResponse] = JsonReader.builder
    .addField[String]("statementText")
    .addField[Seq[StreamInfo]]("streams")
    .buildReader(StreamResponse.apply)

  implicit val writer: JsonWriter[StreamResponse] = JsonWriter
    .obj[StreamResponse]
    .addField("statementText")(_.statement)
    .addField("streams")(_.streams)

}
