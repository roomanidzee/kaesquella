package com.romanidze.kaesquella.core.models.ksql.info

case class SchemaInfo(
  schemaType: String,
  memberSchema: Option[SchemaInfo],
  fields: Option[Seq[FieldInfo]]
)
