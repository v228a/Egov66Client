package com.vovka.egov66client.data.repo

import android.util.Log
import com.vovka.egov66client.data.mapper.HomeWorkMapper
import com.vovka.egov66client.data.mapper.grades.PeriodMapper
import com.vovka.egov66client.data.mapper.grades.SubjectsMapper
import com.vovka.egov66client.data.mapper.grades.WeekMapper
import com.vovka.egov66client.data.mapper.grades.YearMapper
import com.vovka.egov66client.data.source.GradesNetworkDataSource
import com.vovka.egov66client.data.source.HomeWorkNetworkDataSource
import com.vovka.egov66client.data.source.StudentStorageDataSource
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import dagger.Lazy
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vovka.egov66client.data.mapper.grades.ClassesMapper

@Reusable
class GradesRepositoryImpl @Inject constructor(
    private val gradesNetworkDataSource: Lazy<GradesNetworkDataSource>,
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val yearMapper: Lazy<YearMapper>,
    private val subjectsMapper: Lazy<SubjectsMapper>,
    private val periodMapper: Lazy<PeriodMapper>,
    private val weekMapper: Lazy<WeekMapper>,
    private val classesMapper: Lazy<ClassesMapper>
): GradesRepository {


    override suspend fun getCurrentYearText(): Result<String> {
        //2024-2025
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getYears(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
            ).fold(
                onSuccess = { value -> Result.success(value.currentYear.text) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getCurrentYearId(): Result<String> {
        //2024
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getYears(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
            ).fold(
                onSuccess = { value -> Result.success(value.currentYear.text) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getWeekGrades(
        periodId: String,
        subjectId: String,
        weekNumber: Int?,
        schoolYearId: String
    ): Result<List<GradeWeekEntity>> {
        return withContext(Dispatchers.IO){
            gradesNetworkDataSource.get().getWeekGrades(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                schoolYear = schoolYearId,
                periodId = periodId,
                subjectId = subjectId,
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                weekNumber = 2,
                classId = getClassId().getOrNull().toString() //TODO Fix
                //TODO classId меняется каждый год, и нужно подогнать его под каждый год
            ).fold(
                onSuccess = { value -> weekMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getClassId(): Result<String> {
        return withContext(Dispatchers.IO){
            gradesNetworkDataSource.get().getClasses(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = getCurrentYearId().getOrNull().toString()
            ).fold(
                onSuccess = {value -> classesMapper.get().invoke(value)},
                onFailure = { error -> Result.failure(error)}
            )
        }
    }


    override suspend fun getYears(): Result<List<YearsEntity>> {
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getYears(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
            ).fold(
                onSuccess = { value -> yearMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getPeriods(): Result<List<PeriodEntity>> {
        return withContext(Dispatchers.IO) {

            gradesNetworkDataSource.get().getPeriods(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = getCurrentYearText().getOrNull().toString()
            ).fold(
                onSuccess = { value -> periodMapper.get().invoke(value) },
                onFailure = { error ->
                Log.d("f",error.message.toString())
                    Result.failure(error)
                }
            )
        }
    }

    override suspend fun getSubjects(): Result<List<SubjectEntity>> {
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getSubjects(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = getCurrentYearText().getOrNull().toString()
            ).fold(
                onSuccess = { value -> subjectsMapper.get().invoke(value) },
                onFailure = { error ->
                    Log.d("GradesRepository", error.message.toString())
                    Result.failure(error)
                }
            )
        }
    }
}