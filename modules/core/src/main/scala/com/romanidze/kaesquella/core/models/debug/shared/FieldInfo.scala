package com.romanidze.kaesquella.core.models.debug.shared

import tethys._
import tethys.jackson._

/**
 * Object for representing the information about field from data
 * @param name title of the field
 * @param schema schema representation of field
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class FieldInfo(name: String, schema: SchemaInfo)

object FieldInfo {

  implicit val reader: JsonReader[FieldInfo] = JsonReader.builder
    .addField[String]("name")
    .addField[SchemaInfo]("schema")
    .buildReader(FieldInfo.apply)

  implicit val writer: JsonWriter[FieldInfo] = JsonWriter
    .obj[FieldInfo]
    .addField("name")(_.name)
    .addField("schema")(_.schema)

}
