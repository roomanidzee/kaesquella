package com.romanidze.kaesquella.core.models.query.row

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Response with data from KSQL query
 *
 * @param row row with data
 * @param errorMessage error message in case of fail, in success case - null
 * @param finalMessage possible final message
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class RowInfo(row: Data, errorMessage: Option[String], finalMessage: Option[String])

object RowInfo {

  implicit val reader: JsonReader[RowInfo] = jsonReader[RowInfo]
  implicit val writer: JsonWriter[RowInfo] = jsonWriter[RowInfo]

}
