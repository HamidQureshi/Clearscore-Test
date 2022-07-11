package com.clearscore.test.data.repository

import android.util.Log
import com.clearscore.test.data.dataSource.FetchScoreDataSource
import com.clearscore.test.data.repository.model.ScoreDataResult
import javax.inject.Inject

internal class ScoreDataRepositoryImpl @Inject constructor(
    private val dataSource: FetchScoreDataSource
) : ScoreDataRepository {
    override suspend fun getScore(): ScoreDataResult =
        dataSource.getScoreDetails().let {
            Log.e("----->", "${it.body()}")
            if (it.isSuccessful) {
                ScoreDataResult.Success(it.body()?.accountIDVStatus ?: "")
            } else {
                ScoreDataResult.Failure
            }
        } ?: ScoreDataResult.Failure
}