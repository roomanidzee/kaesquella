package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._

case class VersionInfo(version: String, clusterID: String, serviceID: String)

object VersionInfo {

  implicit val reader: JsonReader[VersionInfo] = JsonReader.builder
    .addField[String]("version")
    .addField[String]("kafkaClusterId")
    .addField[String]("ksqlServiceId")
    .buildReader(VersionInfo.apply)

  implicit val writer: JsonWriter[VersionInfo] = JsonWriter
    .obj[VersionInfo]
    .addField("version")(_.version)
    .addField("kafkaClusterId")(_.clusterID)
    .addField("ksqlServiceId")(_.serviceID)

}

case class KSQLVersionResponse(info: VersionInfo)

object KSQLVersionResponse {

  implicit val reader: JsonReader[KSQLVersionResponse] = JsonReader.builder
    .addField[VersionInfo]("KsqlServerInfo")
    .buildReader(KSQLVersionResponse.apply)

  implicit val writer: JsonWriter[KSQLVersionResponse] = JsonWriter
    .obj[KSQLVersionResponse]
    .addField("KsqlServerInfo")(_.info)

}
