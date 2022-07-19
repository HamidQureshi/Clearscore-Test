package com.clearscore.data.dataSource

import com.clearscore.data.dataSource.model.ScoreDetailsResponse
import retrofit2.Response

interface FetchScoreDataSource {
    suspend fun getScoreDetails(): Response<ScoreDetailsResponse>
}