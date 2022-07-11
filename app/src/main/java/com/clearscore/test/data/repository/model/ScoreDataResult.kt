package com.clearscore.test.data.repository.model

sealed class ScoreDataResult {
    data class Success(
        val accountIDVStatus: String
    ) : ScoreDataResult()

    object Failure : ScoreDataResult()
}
