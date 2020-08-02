package com.romanidze.kaesquella.core.models.ksql.stream

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Class with information about retrieved streams
 *
 * @param name stream name
 * @param topic topic, which is consumed by stream
 * @param format serialization format for data in stream (JSON, AVRO, DELIMITED)
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class StreamInfo(name: String, topic: String, format: String)

object StreamInfo {

  implicit val reader: JsonReader[StreamInfo] = jsonReader[StreamInfo]
  implicit val writer: JsonWriter[StreamInfo] = jsonWriter[StreamInfo]

}
