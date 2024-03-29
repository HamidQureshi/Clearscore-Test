package com.clearscore.data.dataSource.model

import com.google.gson.annotations.SerializedName

data class ScoreDetailsResponse(
    @SerializedName("accountIDVStatus") val accountIDVStatus: String,
    @SerializedName("dashboardStatus") val dashboardStatus: String,
    @SerializedName("creditReportInfo") val creditReportInfo: CreditReportInfo
) {
    data class CreditReportInfo(
        @SerializedName("score") val score: Int,
        @SerializedName("scoreBand") val scoreBand: Int,
        @SerializedName("maxScoreValue") val maxScoreValue: Int
    )
}
