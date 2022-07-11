package com.clearscore.test.data.repository

import com.clearscore.test.data.repository.model.ScoreDataResult

interface ScoreDataRepository {
    suspend fun getScore(): ScoreDataResult
}