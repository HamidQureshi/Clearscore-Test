package com.clearscore.test.data.repository

import com.clearscore.test.data.dataSource.FetchScoreDataSource
import com.clearscore.test.data.repository.model.ScoreDataResult
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

internal class ScoreDataRepositoryImpl @Inject constructor(
    private val dataSource: FetchScoreDataSource
) : ScoreDataRepository {
    override suspend fun getScore(): ScoreDataResult =
        retrofitApiCall(
            call = { dataSource.getScoreDetails() },
            success = { response ->
                ScoreDataResult.Success(
                    accountIDVStatus = response.accountIDVStatus,
                    dashboardStatus = response.dashboardStatus,
                    score = response.creditReportInfo.score,
                    maxScore = response.creditReportInfo.maxScoreValue
                )
            },
            failure = { _, _ -> ScoreDataResult.ServerError },
            noInternet = { ScoreDataResult.NoInternet }
        )
}

// Helper function to parse response
private suspend fun <T, U> retrofitApiCall(
    call: suspend () -> Response<T>,
    success: (T) -> U,
    failure: (String?, Int) -> U,
    noInternet: () -> U
): U {

    return try {
        val response = call()
        val body = response.body()

        when {
            response.isSuccessful && body != null -> success(body)
            response.isSuccessful && body == null -> failure("No Body", response.code())
            else -> failure(response.errorBody()?.string(), response.code())
        }
    } catch (e: IOException) {
        noInternet()
    }
}