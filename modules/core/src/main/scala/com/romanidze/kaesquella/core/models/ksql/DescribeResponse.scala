package com.romanidze.kaesquella.core.models.ksql

object DescribeResponse {

  case class SchemaInfo(
    schemaType: String,
    memberSchema: Option[SchemaInfo],
    fields: Option[Seq[FieldInfo]]
  )

  case class FieldInfo(name: String, schema: SchemaInfo)

  case class SourceDescription(
    name: String,
    readQueries: Seq[String],
    writeQueries: Seq[String],
    fields: Seq[FieldInfo],
    sourceType: String,
    keyColumn: String,
    timestampColumn: String,
    format: String,
    topic: String,
    extended: Boolean,
    statistics: Option[String],
    errorStats: Option[String],
    replication: Option[Int],
    partitions: Option[Int]
  )

  case class DescribeObject(statementText: String, description: SourceDescription)
      extends KSQLResponse

}
