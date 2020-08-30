package com.romanidze.kaesquella.monix.client

import sttp.client._
import sttp.client.asynchttpclient.monix._
import com.romanidze.kaesquella.core.client.ClientInterpreter
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.{ksql, processBody, ClientError, KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import monix.eval.Task
import org.asynchttpclient.{AsyncHttpClient, DefaultAsyncHttpClient}

class MonixClientInterpreter(baseURL: String, httpClient: AsyncHttpClient)
    extends ClientInterpreter[Task] {

  def this(baseURL: String) = this(baseURL, new DefaultAsyncHttpClient())

  private implicit val backend: MonixBackend = AsyncHttpClientMonixBackend.usingClient(httpClient)

  /**
   * Method for retrieving information about query status
   *
   * @return query status
   */
  override def getQueryStatus(queryID: String): Task[Either[ClientError, StatusInfo]] = {

    val requestURL: String = s"${baseURL}/status/${queryID}"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[StatusInfo](elem.body))

  }

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  override def getServerVersion: Task[Either[ClientError, KSQLVersionResponse]] = {

    val requestURL: String = s"${baseURL}/info"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/json")
      .header("Content-Type", "application/json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[KSQLVersionResponse](elem.body))

  }

  /**
   * Method for retrieving results of info raw request
   *
   * @param request request instance with query (queries) and properties
   * @return [[com.romanidze.kaesquella.core.models.ksql.KSQLResponse]] child instance
   */
  override def runInfoRequest(
    request: KSQLInfoRequest
  ): Task[Either[ClientError, ksql.KSQLResponse]] = ???

  /**
   * Method for retrieving results of select request
   *
   * @param request request instance with query and properties
   * @return row information with data
   */
  override def runQueryRequest(request: KSQLQueryRequest): Task[Either[ClientError, RowInfo]] = ???

  /**
   * Execution for CREATE / DROP / TERMINATE commands
   *
   * @param request request instance with query (queries) and properties
   * @return information about execution result
   */
  override def runDDLRequest(request: KSQLInfoRequest): Task[Either[ClientError, DDLInfo]] = ???

  /**
   * Execution for SHOW STREAMS command
   *
   * @return information about KSQL streams
   */
  override def getStreams: Task[Either[ClientError, StreamResponse]] = ???

  /**
   * Execution for SHOW TABLES command
   *
   * @return information about KSQL tables
   */
  override def getTables: Task[Either[ClientError, TableResponse]] = ???

  /**
   * Execution for SHOW QUERIES command
   *
   * @return information about KSQL queries
   */
  override def getQueries: Task[Either[ClientError, QueryResponse]] = ???
}
