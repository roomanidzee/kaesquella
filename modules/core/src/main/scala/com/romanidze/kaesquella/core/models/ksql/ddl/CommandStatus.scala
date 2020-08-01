package com.romanidze.kaesquella.core.models.ksql.ddl

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

case class CommandStatus(status: String, message: String)

object CommandStatus {

  implicit val reader: JsonReader[CommandStatus] = jsonReader[CommandStatus]
  implicit val writer: JsonWriter[CommandStatus] = jsonWriter[CommandStatus]

}
