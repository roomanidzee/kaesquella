package com.romanidze.kaesquella.core.models.query.row

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

case class RowInfo(row: Data, errorMessage: Option[String], finalMessage: Option[String])

object RowInfo {

  implicit val reader: JsonReader[RowInfo] = jsonReader[RowInfo]
  implicit val writer: JsonWriter[RowInfo] = jsonWriter[RowInfo]

}
