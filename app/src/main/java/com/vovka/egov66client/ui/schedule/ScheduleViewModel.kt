package com.vovka.egov66client.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.schedule.GetScheduleWeekUseCase
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleWeekUseCase: GetScheduleWeekUseCase
) : ViewModel() {

    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    private val _weekSchedule = MutableLiveData<WeekScheduleEntity>()
    val weekSchedule: LiveData<WeekScheduleEntity> = _weekSchedule

    fun getWeekSchedule() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                getScheduleWeekUseCase.invoke()
                    .onSuccess { schedule ->
                        _weekSchedule.value = schedule
                        _state.value = State.Success
                    }
                    .onFailure { error ->
                        _state.value = State.Error(error.message ?: "Unknown error")
                    }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed interface State {
        data object Loading : State
        data object Success : State
        data class Error(val message: String) : State
    }

    sealed interface Action {

    }

    companion object {
        val initialState = State.Loading
    }
}