package com.vovka.egov66client.ui.schedule.day

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DayViewModel @Inject constructor() : ViewModel() {





    sealed interface State {
        data object Loading : State
    }

    sealed interface Action {

    }
}