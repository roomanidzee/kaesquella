package com.romanidze.kaesquella.core.models.query.row

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._
import tethys.json4s._

import org.json4s.JsonAST._

case class Data(columns: JArray)

object Data {

  implicit val reader: JsonReader[Data] = jsonReader[Data]
  implicit val writer: JsonWriter[Data] = jsonWriter[Data]

}
