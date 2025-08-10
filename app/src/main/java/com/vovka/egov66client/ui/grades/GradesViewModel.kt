package com.vovka.egov66client.ui.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.grades.GetCurrentYearUseCase
import com.vovka.egov66client.domain.grades.GetGradesUseCase
import com.vovka.egov66client.domain.grades.GetPeriodUseCase
import com.vovka.egov66client.domain.grades.GetSchoolYearsUseCase
import com.vovka.egov66client.domain.grades.GetSubjectsUseCase
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject



@HiltViewModel
class GradesViewModel @Inject constructor(
    val getSchoolYearsUseCase: GetSchoolYearsUseCase,
    val getSubjectsUseCase: GetSubjectsUseCase,
    val getPeriodUseCase: GetPeriodUseCase,
    val getGradesUseCase: GetGradesUseCase,
    val getCurrentYearUseCase: GetCurrentYearUseCase,

) : ViewModel() {


    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()


    //Используется на холодную
    fun loadAllSettings(){
        viewModelScope.launch {
            _action.emit(Action.UpdatePeriod(getPeriodUseCase.invoke().getOrNull()))
            _action.emit(Action.UpdateYear(getSchoolYearsUseCase.invoke().getOrNull()))
            _action.emit(Action.UpdateSubject(getSubjectsUseCase.invoke().getOrNull()))
        }
    }

    fun getCurrentYear(): YearsEntity {
        return runBlocking {
            getCurrentYearUseCase.invoke().getOrNull()!!
        }
    }

    fun updatePeriodAndSubject(yearText: String){
        viewModelScope.launch {
            _action.emit(Action.UpdateSubject(getSubjectsUseCase.invoke(yearText).getOrNull()))
            _action.emit(Action.UpdatePeriod(getPeriodUseCase.invoke(yearText).getOrNull()))
        }
    }

    fun loadGrades(
        subjectId: String,
        periodId: String,
        yearId: String,
        weekNumber: Int?
    ) {
        viewModelScope.launch {
            getGradesUseCase.invoke(
                periodId = periodId,
                subjectId = subjectId,
                weekNumber = weekNumber,
                schoolYearId = yearId
            ).fold(
                onSuccess = {
                    when {
                        it.weekGrades != null -> {
                            _action.emit(Action.LoadWeekGrades(it.weekGrades))
                        }
                        it.yearGrades != null -> {
                            _action.emit(Action.LoadYearGrades(it.yearGrades))
                        }
                        it.periodGrades != null -> {
//                            _action.emit(Action.LoadPeriodGrades(it.periodGrades)
                        }
                        else -> {
                            // Все три null — ошибка
                            error("Все таблицы null")
                        }
                    }

                },
                onFailure = { TODO() }

            )
        }
    }


    //Первое фаза - загрузка настроек
    //Вторая фаза - загрузка настроек с настроект
    //Год - фиксированный, от него меняется список предметов
    //Когда менется год, нужно менять
    //3 возможных recycler - недельный, периодичный, итоговый
    //Периодичный - полугодие, четверть
    sealed interface State {
        data object LoadingSettings : State
        data object LoadingGrades: State
    }

    sealed interface Action {
        data class LoadYearGrades(val yearData: List<YearGradeEntity>) : Action
        data class LoadPeriodGrades(val yearData: List<PeriodEntity>) : Action
        data class LoadWeekGrades(val yearData: List<GradeWeekEntity>) : Action
        data class UpdateSubject(val subjectData: List<SubjectEntity>?): Action
        data class UpdateYear(val yearData: List<YearsEntity>?): Action
        data class UpdatePeriod(val periodData: List<PeriodEntity>?): Action
    }

    companion object {
        val initialState = State.LoadingSettings
    }
}