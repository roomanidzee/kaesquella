package com.romanidze.kaesquella.core.models.ksql.info.explain

import com.romanidze.kaesquella.core.models.ksql.info.FieldInfo

case class QueryDescription(
  statement: String,
  fields: Seq[FieldInfo],
  sources: Seq[String],
  sinks: Seq[String],
  executionPlan: String,
  topology: String
)
