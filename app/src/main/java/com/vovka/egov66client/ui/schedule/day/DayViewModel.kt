package com.vovka.egov66client.ui.schedule.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DayViewModel @Inject constructor() : ViewModel() {

    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    fun showTextHoliday(){
        viewModelScope.launch {
            _action.emit(Action.ShowTextHoliday)
        }
    }
    fun showTextCelebration(){
        viewModelScope.launch {
            _action.emit(Action.ShowTextCelebration)
        }
    }
    fun showTextNoLessons(){
        viewModelScope.launch {
            _action.emit(Action.ShowTextNoLessons)
        }
    }



    sealed interface State {
        data object Loading : State
    }

    sealed interface Action {
        data object ShowTextHoliday: Action
        data object ShowTextCelebration: Action
        data object ShowTextNoLessons: Action

    }

    companion object{
        val initialState = State.Loading
    }
}