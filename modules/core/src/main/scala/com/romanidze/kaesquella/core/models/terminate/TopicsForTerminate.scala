package com.romanidze.kaesquella.core.models.terminate

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

case class TopicsForTerminate(deleteTopicList: List[String])

object TopicsForTerminate {

  implicit val reader: JsonReader[TopicsForTerminate] = jsonReader[TopicsForTerminate]
  implicit val writer: JsonWriter[TopicsForTerminate] = jsonWriter[TopicsForTerminate]

}
