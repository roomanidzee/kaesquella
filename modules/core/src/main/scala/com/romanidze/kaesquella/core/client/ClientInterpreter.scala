package com.romanidze.kaesquella.core.client

import com.romanidze.kaesquella.core.models.ClientError
import com.romanidze.kaesquella.core.models.StatusInfo
import com.romanidze.kaesquella.core.models.VersionInfo
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.ksql.KSQLResponse
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse

/**
 * Trait for describing common client operations. May be changed!
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @version 0.0.1
 */
trait ClientInterpreter[F[_]] {

  /**
   * Method for retrieving information about server status
   *
   * @return server status
   */
  def getServerStatus: F[Either[ClientError, StatusInfo]]

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  def getServerVersion: F[Either[ClientError, VersionInfo]]

  /**
   * Method for retrieving results of info raw request
   *
   * @param request request instance with query (queries) and properties
   * @return [[com.romanidze.kaesquella.core.models.ksql.KSQLResponse]] child instance
   */
  def runInfoRequest(request: KSQLInfoRequest): F[Either[ClientError, KSQLResponse]]

  /**
   * Method for retrieving results of select request
   *
   * @param request request instance with query and properties
   * @return row information with data
   */
  def runQueryRequest(request: KSQLQueryRequest): F[Either[ClientError, RowInfo]]

  /**
   * Execution for CREATE / DROP / TERMINATE commands
   *
   * @param request request instance with query (queries) and properties
   * @return information about execution result
   */
  def runDDLRequest(request: KSQLInfoRequest): F[Either[ClientError, DDLInfo]]

  /**
   * Execution for SHOW STREAMS command
   *
   * @return information about KSQL streams
   */
  def getStreams: F[Either[ClientError, StreamResponse]]

  /**
   * Execution for SHOW TABLES command
   *
   * @return information about KSQL tables
   */
  def getTables: F[Either[ClientError, TableResponse]]

  /**
   * Execution for SHOW QUERIES command
   *
   * @return information about KSQL queries
   */
  def getQueries: F[Either[ClientError, QueryResponse]]

}
