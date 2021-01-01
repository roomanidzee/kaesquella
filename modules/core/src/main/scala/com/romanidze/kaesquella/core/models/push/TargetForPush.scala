package com.romanidze.kaesquella.core.models.push

import tethys._
import tethys.derivation.semiauto._

case class TargetForPush(target: String)

object TargetForPush {

  implicit val reader: JsonReader[TargetForPush] = jsonReader[TargetForPush]
  implicit val writer: JsonWriter[TargetForPush] = jsonWriter[TargetForPush]

}
