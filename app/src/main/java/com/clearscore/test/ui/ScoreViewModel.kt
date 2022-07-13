package com.clearscore.test.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clearscore.test.data.repository.ScoreDataRepository
import com.clearscore.test.data.repository.model.ScoreDataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScoreViewModel @Inject constructor(
    private val repository: ScoreDataRepository
) : ViewModel() {

    private val _state = MutableLiveData<UIState>()

    val state: LiveData<UIState>
        get() = _state

    init {
        getCreditScore()
    }

    fun onAction(creditScoreIntent: CreditScoreIntent) {
        when (creditScoreIntent) {
            is CreditScoreIntent.RefreshCreditScore -> getCreditScore()
        }
    }

    private fun getCreditScore() = viewModelScope.launch {
        _state.postValue(UIState.Loading)

        when (val result = repository.getScore()) {
            ScoreDataResult.Failure -> {
                _state.postValue(UIState.Error)
            }
            is ScoreDataResult.Success -> {
                _state.postValue(
                    UIState.Success(
                        score = result.score,
                        maxScore = result.maxScore,
                        creditRingProgress = calculateCreditRingProgress(
                            result.score,
                            result.maxScore
                        )
                    )
                )
            }
        }
    }

    private fun calculateCreditRingProgress(score: Int, maxScore: Int): Float {
        return score.toFloat() / maxScore
    }

}