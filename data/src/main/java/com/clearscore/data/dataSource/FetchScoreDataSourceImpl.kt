package com.clearscore.data.dataSource

import com.clearscore.data.api.GetScoreApi
import com.clearscore.data.dataSource.model.ScoreDetailsResponse
import retrofit2.Response
import javax.inject.Inject

internal class FetchScoreDataSourceImpl @Inject constructor(
    private val getScoreApi: GetScoreApi
) : FetchScoreDataSource {

    override suspend fun getScoreDetails(): Response<ScoreDetailsResponse> {
        return getScoreApi.getScore()
    }

}