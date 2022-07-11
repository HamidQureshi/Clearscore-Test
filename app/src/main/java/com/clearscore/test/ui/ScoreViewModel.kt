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
class ScoreViewModel @Inject constructor(
    private val repository: ScoreDataRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state

//    init {
//        getCreditScore()
//    }

    fun getCreditScore() = viewModelScope.launch {
        _state.postValue(State.Loading)

        when (repository.getScore()) {
            ScoreDataResult.Failure -> {
                _state.postValue(State.Error)
            }
            is ScoreDataResult.Success -> {
                _state.postValue(State.Success)
            }
        }
    }

}

sealed class State {
    object Loading : State()
    object Success : State()
    object Error : State()
}