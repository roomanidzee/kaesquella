package com.romanidze.kaesquella.core.models.debug.shared

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Schema representation for the data
 * @param `type`  data type
 * @param memberSchema inner schema , if the data type is MAP or ARRAY, otherwise - None
 * @param fields list of field objects if the data type is STRUCT, otherwise - None
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class SchemaInfo(
  `type`: String,
  memberSchema: Option[SchemaInfo],
  fields: Option[List[FieldInfo]]
)

object SchemaInfo {

  implicit val reader: JsonReader[SchemaInfo] = jsonReader[SchemaInfo]
  implicit val writer: JsonWriter[SchemaInfo] = jsonWriter[SchemaInfo]

}
