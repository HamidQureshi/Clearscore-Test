package com.clearscore.test.data.dataSource

import com.clearscore.test.data.dataSource.model.ScoreDetailsResponse
import retrofit2.Response

interface FetchScoreDataSource {
    suspend fun getScoreDetails(): Response<ScoreDetailsResponse>
}