package com.clearscore.data.repository

import com.clearscore.data.repository.model.ScoreDataResult

interface ScoreDataRepository {
    suspend fun getScore(): ScoreDataResult
}