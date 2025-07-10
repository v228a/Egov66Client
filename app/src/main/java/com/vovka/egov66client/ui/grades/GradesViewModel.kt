package com.vovka.egov66client.ui.grades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.core.Constants
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

    private var loadedYears: List<YearsEntity>? = null
    private var loadedSubjects: List<SubjectEntity>? = null
    private var loadedPeriods: List<PeriodEntity>? = null

    fun loadYears() {
        _state.value = State.SettingsLoad
        viewModelScope.launch {
            val years = getSchoolYearsUseCase.invoke().getOrNull()
            loadedYears = years
            _action.emit(Action.ShowYears(years))
            checkAllSettingsLoaded()
        }
    }

    fun loadSubjects(schoolYear: String = "") {
        _state.value = State.SettingsLoad
        viewModelScope.launch {
            val subjects = getSubjectsUseCase.invoke(
                schoolYear = schoolYear
            ).getOrNull()
            loadedSubjects = subjects
            _action.emit(Action.ShowSubjects(subjects))
            checkAllSettingsLoaded()
        }
    }

    fun loadPeriods() {
        _state.value = State.SettingsLoad
        viewModelScope.launch {
            val periods = getPeriodUseCase.invoke().getOrNull()
            loadedPeriods = periods
            _action.emit(Action.ShowPeriods(periods))
            checkAllSettingsLoaded()
        }
    }

    fun loadAllSettings() {
        loadedYears = null
        loadedSubjects = null
        loadedPeriods = null
        loadYears()
        loadSubjects()
        loadPeriods()
    }

    private fun checkAllSettingsLoaded() {
        if (loadedYears != null && loadedSubjects != null && loadedPeriods != null) {
            _action.tryEmit(Action.ShowSettings(
                yearData = loadedYears,
                subjectData = loadedSubjects,
                periodData = loadedPeriods
            ))
            // Optionally, trigger loading grades here or let Fragment do it
        }
    }

    fun loadWeekGrades(
        subjectId: String = Constants.ALL_SUBJECTS_ID,
        weekNumber: Int? = null,
        periodId: String? = "",
        schoolYear: String = ""
    ){
        if (periodId.isNullOrEmpty()){
            return
        }
        viewModelScope.launch {
            _action.emit(Action.ShowWeekGrades(
                getWeekGradesUseCase.invoke(
                    periodId = periodId,
                    subjectId = subjectId,
                    weekNumber = weekNumber,
                    schoolYearId = schoolYear
                ).fold(
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
        data class ShowYears(val yearData: List<YearsEntity>?) : Action
        data class ShowSubjects(val subjectData: List<SubjectEntity>?) : Action
        data class ShowPeriods(val periodData: List<PeriodEntity>?) : Action
        data object ChangePeriod : Action
        data object ChangeSubject : Action
    }

    companion object {
        val initialState = State.SettingsLoad
    }
}