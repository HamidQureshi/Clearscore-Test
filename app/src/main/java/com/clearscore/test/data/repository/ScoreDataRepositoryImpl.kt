package com.clearscore.test.data.repository

import com.clearscore.test.data.dataSource.FetchScoreDataSource
import com.clearscore.test.data.repository.model.ScoreDataResult
import javax.inject.Inject

internal class ScoreDataRepositoryImpl @Inject constructor(
    private val dataSource: FetchScoreDataSource
) : ScoreDataRepository {
    override suspend fun getScore(): ScoreDataResult =
        dataSource.getScoreDetails().let {
            if (it.isSuccessful) {
                ScoreDataResult.Success(
                    accountIDVStatus = it.body()?.accountIDVStatus ?: "",
                    score = it.body()?.creditReportInfo?.score ?: 0,
                    maxScore = it.body()?.creditReportInfo?.maxScoreValue ?: 0
                )
            } else {
                ScoreDataResult.Failure
            }
        }
}