package com.vovka.egov66client.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.student.UpdateStudentIdUseCase
import com.vovka.egov66client.domain.student.UpdateTokenUseCase
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val updateStudentIdUseCase: UpdateStudentIdUseCase
): ViewModel() {


    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

//    private val _state = MutableStateFlow<State>(initialState)
//    val state = _state.asStateFlow()


     fun updateToken(token: String){
        viewModelScope.launch {
            updateTokenUseCase.invoke(token)
            updateStudentIdUseCase.invoke()
            _action.emit(Action.OpenSchedule)
        }
    }



    sealed interface State {
        //TODO состояние логина1
    }

    sealed interface Action {
        data object OpenSchedule : Action


    }
    companion object {


//        val initialState = State.Loading
    }
}