package com.romanidze.kaesquella.monix.client

import tethys._
import tethys.jackson._
import sttp.client._
import sttp.client.asynchttpclient.monix._
import com.romanidze.kaesquella.core.client.ClientInterpreter
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.{ksql, ClientError, ExecutionError, KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import monix.eval.Task
import org.asynchttpclient.{AsyncHttpClient, DefaultAsyncHttpClient}
import tethys.readers.ReaderError

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

    response.map(elem => {

      val body: Either[String, String] = elem.body
      val errorMessage = s"Unsuccessful response while retrieving query status for id ${queryID}"

      if (body.isLeft) {

        val errorBody: Either[ReaderError, ExecutionError] =
          body.swap.toOption.get.jsonAs[ExecutionError]

        if (errorBody.isLeft) {
          Left(ClientError(errorMessage, None, Some(errorBody.swap.toOption.get.getMessage)))
        }

        Left(ClientError(errorMessage, Some(errorBody.toOption.get), None))
      } else {

        val queryStatus: Either[ReaderError, StatusInfo] = body.toOption.get.jsonAs[StatusInfo]

        if (queryStatus.isLeft) {
          Left(ClientError(errorMessage, None, Some(queryStatus.swap.toOption.get.getMessage)))
        }

        Right(queryStatus.toOption.get)
      }

    })

  }

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  override def getServerVersion: Task[Either[ClientError, KSQLVersionResponse]] = ???

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
