package com.romanidze.kaesquella.core.models.push

import tethys._
import tethys.derivation.semiauto._

case class PushResponse(status: String, seq: Int)

object PushResponse {

  implicit val reader: JsonReader[PushResponse] = jsonReader[PushResponse]
  implicit val writer: JsonWriter[PushResponse] = jsonWriter[PushResponse]

}
