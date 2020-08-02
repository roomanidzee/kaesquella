package com.romanidze.kaesquella.core.models.ksql.ddl

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Information about command status
 *
 * @param status command status (may be SUCCESS, FAILED, etc..)
 * @param message Description for status
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class CommandStatus(status: String, message: String)

object CommandStatus {

  implicit val reader: JsonReader[CommandStatus] = jsonReader[CommandStatus]
  implicit val writer: JsonWriter[CommandStatus] = jsonWriter[CommandStatus]

}
