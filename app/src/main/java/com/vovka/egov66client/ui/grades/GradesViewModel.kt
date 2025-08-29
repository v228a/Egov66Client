package com.vovka.egov66client.ui.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.grades.GetCurrentYearUseCase
import com.vovka.egov66client.domain.grades.GetGradesUseCase
import com.vovka.egov66client.domain.grades.GetPeriodUseCase
import com.vovka.egov66client.domain.grades.GetSchoolYearsUseCase
import com.vovka.egov66client.domain.grades.GetSubjectsUseCase
import com.vovka.egov66client.domain.grades.entity.week.WeekGradesListEntity
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodGradeEntity
import com.vovka.egov66client.domain.grades.entity.week.GradeWeekEntity
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

    // Кэш для хранения данных
    public var cachedSubjects: List<SubjectEntity>? = null
    public var cachedPeriods: List<PeriodEntity>? = null
    public var cachedYears: List<YearsEntity>? = null

    //Используется на холодную
    fun loadAllSettings(){
        viewModelScope.launch {
            val periods = getPeriodUseCase.invoke().getOrNull()
            val years = getSchoolYearsUseCase.invoke().getOrNull()
            val subjects = getSubjectsUseCase.invoke().getOrNull()
            
            cachedPeriods = periods
            cachedYears = years
            cachedSubjects = subjects
            
            _action.emit(Action.UpdatePeriod(periods))
            _action.emit(Action.UpdateYear(years))
            _action.emit(Action.UpdateSubject(subjects))
        }
    }

    fun getCurrentYear(): YearsEntity {
        return runBlocking {
            getCurrentYearUseCase.invoke().getOrNull()!!
        }
    }

    fun updatePeriodAndSubject(yearText: String){
        viewModelScope.launch {
            val subjects = getSubjectsUseCase.invoke(yearText).getOrNull()
            val periods = getPeriodUseCase.invoke(yearText).getOrNull()
            
            cachedSubjects = subjects
            cachedPeriods = periods
            
            _action.emit(Action.UpdateSubject(subjects))
            _action.emit(Action.UpdatePeriod(periods))
        }
    }

    fun getSubjectIdByName(name: String): String {
        return cachedSubjects?.find { it.name == name }?.id ?: ""
    }

    fun getPeriodIdByName(name: String): String {
        return cachedPeriods?.find { it.name == name }?.id ?: ""
    }

    fun getYearIdByName(name: String): String {
        return cachedYears?.find { it.name == name }?.id ?: ""
    }


    fun loadGrades(
        subjectId: String,
        periodId: String,
        yearId: String,
        weekNumber: Int?
    ) {
        viewModelScope.launch {
            // Устанавливаем состояние загрузки
            _state.value = State.LoadingGrades
            
            getGradesUseCase.invoke(
                periodId = periodId,
                subjectId = subjectId,
                weekNumber = weekNumber,
                schoolYearId = yearId
            ).fold(
                onSuccess = {
                    when {
                        it.weekGrades != null -> {
                            _state.value = State.LoadWeekGrades(it.weekGrades)
                        }
                        it.yearGrades != null -> {
                            _state.value = State.LoadYearGrades(it.yearGrades)
                        }
                        it.periodGrades != null -> {
                            _state.value = State.LoadPeriodGrades(it.periodGrades)
                        }
                        else -> {
                            // Все три null — ошибка
                            _state.value = State.Error("Все таблицы null")
                        }
                    }

                },
                onFailure = { 
                    _state.value = State.Error(it.localizedMessage ?: "Неизвестная ошибка")
                }

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
        data object LoadingGrades : State
        data class LoadYearGrades(val yearData: List<YearGradeEntity>) : State
        data class LoadPeriodGrades(val periodData: List<PeriodGradeEntity>) : State
        data class LoadWeekGrades(val weekData: GradeWeekEntity) : State
        data class Error(val message: String) : State
    }

    sealed interface Action {
        data class UpdateSubject(val subjectData: List<SubjectEntity>?): Action
        data class UpdateYear(val yearData: List<YearsEntity>?): Action
        data class UpdatePeriod(val periodData: List<PeriodEntity>?): Action
    }

    companion object {
        val initialState = State.LoadingSettings
    }
}