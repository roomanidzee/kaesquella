package com.romanidze.kaesquella.core.models.debug.explain

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

import com.romanidze.kaesquella.core.models.debug.shared.FieldInfo

/**
 * Case class for the definition of "EXPLAIN" query result
 * @param statementText The ksqlDB statement for which the query being explained is running.
 * @param fields A list of field objects that describes each field in the query output.
 * @param sources The streams and tables being read by the query.
 * @param sinks The streams and tables being written to by the query.
 * @param executionPlan They query execution plan.
 * @param topology The Kafka Streams topology that the query is running.
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class QueryDescription(
  statementText: String,
  fields: List[FieldInfo],
  sources: List[String],
  sinks: List[String],
  executionPlan: String,
  topology: String
)

object QueryDescription {

  implicit val reader: JsonReader[QueryDescription] = jsonReader[QueryDescription]
  implicit val writer: JsonWriter[QueryDescription] = jsonWriter[QueryDescription]

}

/**
 * Case class for holding the "DESCRIBE" result
 * @param queryDescription result of "explain" query
 * @param overriddenProperties The property overrides that the query is running with.
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class ExplainResult(
  queryDescription: QueryDescription,
  overriddenProperties: Map[String, String]
)

object ExplainResult {

  implicit val reader: JsonReader[ExplainResult] = jsonReader[ExplainResult]
  implicit val writer: JsonWriter[ExplainResult] = jsonWriter[ExplainResult]

}
