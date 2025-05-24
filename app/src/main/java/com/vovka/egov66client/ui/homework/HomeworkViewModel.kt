package com.vovka.egov66client.ui.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovka.egov66client.ui.schedule.ScheduleViewModel.Action
import com.vovka.egov66client.ui.schedule.ScheduleViewModel.Companion.initialState
import com.vovka.egov66client.ui.schedule.ScheduleViewModel.State
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeworkViewModel @Inject constructor(): ViewModel() {

    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

//    private val _state = MutableStateFlow<State>(initialState)
//    val state = _state.asStateFlow()




//    sealed interface State {
//
//    }

    sealed interface Action {

    }

//    companion object{
//        val initialState = State.Loading
//    }
}