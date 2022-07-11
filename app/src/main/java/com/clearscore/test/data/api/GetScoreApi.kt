package com.clearscore.test.data.api

import com.clearscore.test.data.dataSource.model.ScoreDetailsResponse
import retrofit2.Response
import retrofit2.http.GET

interface GetScoreApi {
    @GET("endpoint.json")
    suspend fun getScore(): Response<ScoreDetailsResponse>
}