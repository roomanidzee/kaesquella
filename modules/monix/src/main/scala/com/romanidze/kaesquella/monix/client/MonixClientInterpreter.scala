package com.romanidze.kaesquella.monix.client

import tethys._
import tethys.jackson._
import sttp.client.okhttp.monix._
import sttp.client.okhttp.WebSocketHandler
import com.romanidze.kaesquella.core.client.{ClientFPInterpreter, ClientInterpreter}
import com.romanidze.kaesquella.core.client.Output
import com.romanidze.kaesquella.core.models.debug.describe.DescribeResult
import com.romanidze.kaesquella.core.models.debug.explain.ExplainResult
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.{processBody, processLeft, ExecutionError, KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.pull.{PullRequest, PullResponse}
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.terminate.TopicsForTerminate
import com.romanidze.kaesquella.monix.utils.{processPullQuery, processRowInfo}
import monix.eval.Task
import monix.reactive.Observable
import okhttp3.OkHttpClient

class MonixClientInterpreter(baseURL: String, httpClient: OkHttpClient)
    extends ClientFPInterpreter[Task, Observable, WebSocketHandler](baseURL)
    with ClientInterpreter[Task, Observable] {

  def this(baseURL: String) = this(baseURL, new OkHttpClient())

  implicit override val backend: MonixBackend = OkHttpMonixBackend.usingClient(httpClient)

  /**
   * Method for retrieving information about query status
   *
   * @return query status
   */
  override def getQueryStatus(queryID: String): Task[Output[StatusInfo]] =
    sendGETRequest(s"$baseURL/status/$queryID").map(elem => processBody[StatusInfo](elem.body))

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  override def getServerVersion: Task[Output[KSQLVersionResponse]] =
    sendGETRequest(s"$baseURL/info")
      .map(elem => processBody[KSQLVersionResponse](elem.body))

  /**
   * Method for retrieving results of select request
   *
   * @param inputRequest request instance with query and properties
   * @return row information with data
   */
  override def runQueryRequest(inputRequest: KSQLQueryRequest): RowInfoResponse =
    sendStreamRequest(s"$baseURL/query", inputRequest.asJson)
      .map(elem => processRowInfo(elem.body))

  /**
   * Execution for CREATE / DROP / TERMINATE commands
   *
   * @param request request instance with query (queries) and properties
   * @return information about execution result
   */
  override def runDDLRequest(request: KSQLInfoRequest): Task[Output[DDLInfo]] =
    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[DDLInfo](elem.body))

  /**
   * Execution for SHOW STREAMS command
   *
   * @return information about KSQL streams
   */
  override def getStreams: Task[Output[StreamResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW STREAMS", Map.empty[String, String])

    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[StreamResponse](elem.body))

  }

  /**
   * Execution for SHOW TABLES command
   *
   * @return information about KSQL tables
   */
  override def getTables: Task[Output[TableResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW TABLES", Map.empty[String, String])

    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[TableResponse](elem.body))

  }

  /**
   * Execution for SHOW QUERIES command
   *
   * @return information about KSQL queries
   */
  override def getQueries: Task[Output[QueryResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW QUERIES", Map.empty[String, String])

    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[QueryResponse](elem.body))

  }

  /**
   * Describe the KTable or KStream
   *
   * @param sourceName name of a table or stream
   * @param isExtended should the response be extended or not
   * @return result of describe query
   */
  override def describeSource(
    sourceName: String,
    isExtended: Boolean
  ): Task[Output[DescribeResult]] = {

    val query: String = isExtended match {
      case true  => s"DESCRIBE EXTENDED ${sourceName};"
      case false => s"DESCRIBE ${sourceName}"
    }

    val request: KSQLInfoRequest = KSQLInfoRequest(query, Map.empty[String, String])

    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[DescribeResult](elem.body))

  }

  /**
   * Explain the input KSQL query
   *
   * @param queryID KSQL query ID for "EXPLAIN" operation
   * @return result of explain query
   */
  override def explainQuery(queryID: String): Task[Output[ExplainResult]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest(s"EXPLAIN ${queryID}", Map.empty[String, String])

    sendPOSTRequest(s"$baseURL/ksql", Some(request.asJson))
      .map(elem => processBody[ExplainResult](elem.body))

  }

  /**
   * Method for terminating the KSQLDB cluster
   *
   * @param topicsForTerminate optional topics list to delete (WARNING: only generated topics for queries will be deleted, other will not)
   * @return status about termination
   */
  override def terminateCluster(
    topicsForTerminate: Option[TopicsForTerminate]
  ): Task[Output[StatusInfo]] =
    sendPOSTRequest(s"$baseURL/ksql/terminate", topicsForTerminate.map(_.asJson)).map(elem =>
      elem.body match {
        case Left(value) => processLeft[ExecutionError](value)
        case Right(_)    => Right(StatusInfo("200", "Cluster terminated successfully"))
      }
    )

  /**
   * Method for running the KSQL pull request
   *
   * @param request request parameters
   * @return pull response - header or data
   */
  override def runPullRequest(
    request: PullRequest
  ): Task[Output[Observable[Output[PullResponse]]]] = {

    sendStreamRequest(s"$baseURL/query-stream", request.asJson, isPullQuery = true)
      .map(elem => processPullQuery(elem.body))

  }
}
