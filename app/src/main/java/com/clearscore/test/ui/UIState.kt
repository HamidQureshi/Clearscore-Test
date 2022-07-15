package com.clearscore.test.ui

sealed class UIState {
    object Loading : UIState()
    data class Success(
        val score: Int,
        val maxScore: Int,
        val creditRingProgress: Float
    ): UIState()
    object Error : UIState()
    object NoInternet : UIState()
}