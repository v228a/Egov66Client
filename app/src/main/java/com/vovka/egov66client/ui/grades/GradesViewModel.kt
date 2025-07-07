package com.vovka.egov66client.ui.grades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.grades.GetPeriodUseCase
import com.vovka.egov66client.domain.grades.GetSchoolYearsUseCase
import com.vovka.egov66client.domain.grades.GetSubjectsUseCase
import com.vovka.egov66client.domain.grades.GetWeekGradesUseCase
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.ui.login.LoginViewModel
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GradesViewModel @Inject constructor(
    val getSchoolYearsUseCase: GetSchoolYearsUseCase,
    val getSubjectsUseCase: GetSubjectsUseCase,
    val getPeriodUseCase: GetPeriodUseCase,
    val getWeekGradesUseCase: GetWeekGradesUseCase
) : ViewModel() {


    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()




    fun loadSettings(){
        viewModelScope.launch {
            // почините это
            _action.emit(Action.ShowSettings(
                yearData = getSchoolYearsUseCase.invoke().getOrNull(),
                subjectData = getSubjectsUseCase.invoke().getOrNull(),
                periodData = getPeriodUseCase.invoke().getOrNull()
            ))
        }
    }

    fun loadWeekGrades(){
        viewModelScope.launch {
            _action.emit(Action.ShowWeekGrades(
                getWeekGradesUseCase.invoke().fold(
                    onSuccess = {it},
                    onFailure = {TODO()}
                ),
            ))
        }
    }


    sealed interface State {
        data object SettingsLoad : State
        data object GradesLoad : State

    }

    sealed interface Action {
        data object ChangeYear : Action

        data class ShowWeekGrades(
            val grades: List<GradeWeekEntity>
        ) : Action

        data class ShowSettings(
            val yearData: List<YearsEntity>?,
            val subjectData: List<SubjectEntity>?,
            val periodData: List<PeriodEntity>?,
        ) : Action

        data object ChangePeriod : Action
        data object ChangeSubject : Action
    }

    companion object {
        val initialState = State.SettingsLoad
    }
}