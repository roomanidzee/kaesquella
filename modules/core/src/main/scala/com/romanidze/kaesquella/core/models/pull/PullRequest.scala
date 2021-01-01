package com.romanidze.kaesquella.core.models.pull

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Model for representing a pull query request
 * @param sql KSQL pull query string
 * @param properties properties for pull query
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class PullRequest(sql: String, properties: Map[String, String])

object PullRequest {

  implicit val reader: JsonReader[PullRequest] = jsonReader[PullRequest]
  implicit val writer: JsonWriter[PullRequest] = jsonWriter[PullRequest]

}
