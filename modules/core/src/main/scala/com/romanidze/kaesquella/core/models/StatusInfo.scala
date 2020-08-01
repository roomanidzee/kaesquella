package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

case class StatusInfo(status: String, message: String)

object StatusInfo {

  implicit val reader: JsonReader[StatusInfo] = jsonReader[StatusInfo]
  implicit val writer: JsonObjectWriter[StatusInfo] = jsonWriter[StatusInfo]

}
