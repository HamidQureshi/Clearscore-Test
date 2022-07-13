package com.clearscore.test.data.repository.model

sealed class ScoreDataResult {
    data class Success(
        val accountIDVStatus: String,
        val score: Int,
        val maxScore: Int
    ) : ScoreDataResult()

    object Failure : ScoreDataResult()
}
