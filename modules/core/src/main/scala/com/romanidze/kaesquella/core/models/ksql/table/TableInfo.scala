package com.romanidze.kaesquella.core.models.ksql.table

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Class with information about retrieved tables
 *
 * @param name table name
 * @param topic topic, which is consumed by table
 * @param format data serialization format (JSON, AVRO, DELIMITED)
 * @param type type of table
 * @param isWindowed if the table provides windowed results
 */
case class TableInfo(
  name: String,
  topic: String,
  format: String,
  `type`: String,
  isWindowed: Boolean
)

object TableInfo {

  implicit val reader: JsonReader[TableInfo] = jsonReader[TableInfo]
  implicit val writer: JsonWriter[TableInfo] = jsonWriter[TableInfo]

}
