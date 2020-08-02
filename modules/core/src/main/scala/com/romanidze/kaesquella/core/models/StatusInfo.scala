package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Information about KSQL server
 *
 * @param status status code
 * @param message status description
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class StatusInfo(status: String, message: String)

object StatusInfo {

  implicit val reader: JsonReader[StatusInfo] = jsonReader[StatusInfo]
  implicit val writer: JsonObjectWriter[StatusInfo] = jsonWriter[StatusInfo]

}
