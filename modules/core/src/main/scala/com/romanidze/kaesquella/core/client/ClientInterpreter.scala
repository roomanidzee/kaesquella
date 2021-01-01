package com.romanidze.kaesquella.core.client

import com.romanidze.kaesquella.core.models.debug.describe.DescribeResult
import com.romanidze.kaesquella.core.models.debug.explain.ExplainResult
import com.romanidze.kaesquella.core.models.{KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse
import com.romanidze.kaesquella.core.models.pull.{PullRequest, PullResponse}
import com.romanidze.kaesquella.core.models.push.{PushResponse, TargetForPush}
import com.romanidze.kaesquella.core.models.terminate.TopicsForTerminate
import org.json4s.JsonAST.JObject

/**
 * Trait for describing common client operations. May be changed!
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @version 0.0.1
 */
trait ClientInterpreter[F[_], G[_]] {

  /**
   * Method for retrieving information about query status
   *
   * @return query status
   */
  def getQueryStatus(queryID: String): F[Output[StatusInfo]]

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  def getServerVersion: F[Output[KSQLVersionResponse]]

  /**
   * Method for retrieving results of select request
   *
   * @param request request instance with query and properties
   * @return row information with data
   */
  def runQueryRequest(request: KSQLQueryRequest): F[Output[G[Output[RowInfo]]]]

  /**
   * Execution for CREATE / DROP / TERMINATE commands
   *
   * @param request request instance with query (queries) and properties
   * @return information about execution result
   */
  def runDDLRequest(request: KSQLInfoRequest): F[Output[DDLInfo]]

  /**
   * Execution for SHOW STREAMS command
   *
   * @return information about KSQL streams
   */
  def getStreams: F[Output[StreamResponse]]

  /**
   * Execution for SHOW TABLES command
   *
   * @return information about KSQL tables
   */
  def getTables: F[Output[TableResponse]]

  /**
   * Execution for SHOW QUERIES command
   *
   * @return information about KSQL queries
   */
  def getQueries: F[Output[QueryResponse]]

  /**
   * Describe the KTable or KStream
   * @param sourceName name of a table or stream
   * @param isExtended should the response be extended or not
   * @return result of describe query
   */
  def describeSource(sourceName: String, isExtended: Boolean): F[Output[DescribeResult]]

  /**
   * Explain the input KSQL query
   * @param queryID KSQL query ID for "EXPLAIN" operation
   * @return result of explain query
   */
  def explainQuery(queryID: String): F[Output[ExplainResult]]

  /**
   * Method for terminating the KSQLDB cluster
   * @param topicsForTerminate optional topics list to delete (WARNING: only generated topics for queries will be deleted, other will not)
   * @return status about termination
   */
  def terminateCluster(topicsForTerminate: Option[TopicsForTerminate]): F[Output[StatusInfo]]

  /**
   * Method for running the KSQL pull request
   * @param request request parameters
   * @return pull response - header or data
   */
  def runPullRequest(request: PullRequest): F[Output[G[Output[PullResponse]]]]

  /**
   * Method for running the KSQL push request
   * @param request stream for data inserting
   * @param values data for insert
   * @return push response - information for each data value,is it inserted or not
   */
  def runPushRequest(
    request: TargetForPush,
    values: List[JObject]
  ): F[Output[G[Output[PushResponse]]]]

}
