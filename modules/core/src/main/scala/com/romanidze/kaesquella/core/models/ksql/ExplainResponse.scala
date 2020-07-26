package com.romanidze.kaesquella.core.models.ksql

object ExplainResponse {

  case class SchemaInfo(
    schemaType: String,
    memberSchema: Option[SchemaInfo],
    fields: Option[Seq[FieldInfo]]
  )

  case class FieldInfo(name: String, schema: SchemaInfo)

  case class QueryDescription(
    statement: String,
    fields: Seq[FieldInfo],
    sources: Seq[String],
    sinks: Seq[String],
    executionPlan: String,
    topology: String
  )

  case class ExplainObject(
    statement: String,
    description: QueryDescription,
    overridenProperties: Map[String, String]
  ) extends KSQLResponse

}
