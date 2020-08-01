package com.romanidze.kaesquella.core.models.ksql.stream

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

case class StreamInfo(name: String, topic: String, format: String)

object StreamInfo {

  implicit val reader: JsonReader[StreamInfo] = jsonReader[StreamInfo]
  implicit val writer: JsonWriter[StreamInfo] = jsonWriter[StreamInfo]

}
