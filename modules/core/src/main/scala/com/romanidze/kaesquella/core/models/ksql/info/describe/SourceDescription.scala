package com.romanidze.kaesquella.core.models.ksql.info.describe

import com.romanidze.kaesquella.core.models.ksql.info.FieldInfo

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
