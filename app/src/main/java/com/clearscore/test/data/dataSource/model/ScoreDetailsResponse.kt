package com.clearscore.test.data.dataSource.model

import com.google.gson.annotations.SerializedName

data class ScoreDetailsResponse(
    @SerializedName("accountIDVStatus") val accountIDVStatus: String
)
