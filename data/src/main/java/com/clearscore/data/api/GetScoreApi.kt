package com.clearscore.data.api

import com.clearscore.data.dataSource.model.ScoreDetailsResponse
import retrofit2.Response
import retrofit2.http.GET

interface GetScoreApi {
    @GET("endpoint.json")
    suspend fun getScore(): Response<ScoreDetailsResponse>
}