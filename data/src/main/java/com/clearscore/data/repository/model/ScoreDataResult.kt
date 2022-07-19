package com.clearscore.data.repository.model

sealed class ScoreDataResult {
    data class Success(
        val accountIDVStatus: String,
        val dashboardStatus: String,
        val score: Int,
        val maxScore: Int
    ) : ScoreDataResult()

    object ServerError : ScoreDataResult()
    object NoInternet : ScoreDataResult()
}
