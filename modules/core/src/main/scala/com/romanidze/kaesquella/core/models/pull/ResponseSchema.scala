package com.romanidze.kaesquella.core.models.pull

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Schema for the pull query result
 * @param queryId unique ID of a query
 * @param columnNames the names of the columns
 * @param columnTypes The types of the columns
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class ResponseSchema(
  queryId: Option[String],
  columnNames: List[String],
  columnTypes: List[String]
)

object ResponseSchema {

  implicit val reader: JsonReader[ResponseSchema] = jsonReader[ResponseSchema]
  implicit val writer: JsonWriter[ResponseSchema] = jsonWriter[ResponseSchema]

}
