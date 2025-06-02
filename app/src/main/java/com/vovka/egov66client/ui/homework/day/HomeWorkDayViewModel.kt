package com.vovka.egov66client.ui.homework.day

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.homework.GetHomeWorkUseCase
import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.ui.profile.ProfileViewModel

import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class HomeWorkDayViewModel @Inject constructor(
    private val getHomeWorkUseCase: GetHomeWorkUseCase
): ViewModel() {



    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    fun updateHomeWork(date: LocalDateTime){
        viewModelScope.launch {
            _state.update { State.Loading }
            getHomeWorkUseCase.invoke(date).fold(
                onSuccess = { value ->
                    _state.update { State.Show(value) }
                },
                onFailure = { error ->
                    Log.d("F",error.message.toString())
                }
            )
        }
    }

    sealed interface Action {}

    sealed interface State{
        data object Loading : State
        data object Error : State
        data class Show(
            val homeworks: DayHomeWorkEntity
        ) : State

    }
    companion object {
        val initialState = State.Loading
    }
}
