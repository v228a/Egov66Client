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
import javax.inject.Inject
import com.vovka.egov66client.data.mapper.grades.ClassesMapper
import com.vovka.egov66client.data.mapper.grades.YearGradesMapper
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity

@Reusable
class GradesRepositoryImpl @Inject constructor(
    private val gradesNetworkDataSource: Lazy<GradesNetworkDataSource>,
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val yearMapper: Lazy<YearMapper>,
    private val subjectsMapper: Lazy<SubjectsMapper>,
    private val periodMapper: Lazy<PeriodMapper>,
    private val weekMapper: Lazy<WeekMapper>,
    private val classesMapper: Lazy<ClassesMapper>,
    private val yearGradesMapper: Lazy<YearGradesMapper>
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
                onSuccess = { value -> Result.success(value.currentYear.id) },
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
            val schoolYear = if(schoolYearId.isNullOrEmpty()) getCurrentYearId().getOrNull().toString() else schoolYearId
            gradesNetworkDataSource.get().getGrades(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                schoolYear = schoolYear,
                periodId = periodId,
                subjectId = subjectId,
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                weekNumber = weekNumber,
                classId = getClassId(schoolYear).getOrNull().toString()
            ).fold(
                onSuccess = { value -> weekMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getClassId(schoolYear: String): Result<String> {
        return withContext(Dispatchers.IO){
            gradesNetworkDataSource.get().getClasses(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = schoolYear
            ).fold(
                onSuccess = {value -> classesMapper.get().invoke(value)},
                onFailure = { error -> Result.failure(error)}
            )
        }
    }

    override suspend fun getYearGrades(
        periodId: String,
        subjectId: String,
        weekNumber: Int?,
        schoolYearId: String
    ): Result<List<YearGradeEntity>> {
        return withContext(Dispatchers.IO){
            val schoolYear = if(schoolYearId.isNullOrEmpty()) getCurrentYearId().getOrNull().toString() else schoolYearId
            gradesNetworkDataSource.get().getGrades(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                schoolYear = schoolYear,
                periodId = periodId,
                subjectId = subjectId,
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                weekNumber = weekNumber,
                classId = getClassId(schoolYear).getOrNull().toString()
            ).fold(
                onSuccess = {value -> yearGradesMapper.get().invoke(value)},
                onFailure = {error -> Result.failure(error)}
            )
        }
    }

    override suspend fun getPeriodGrades(
        periodId: String,
        subjectId: String,
        weekNumber: Int?,
        schoolYearId: String
    ): Result<List<YearGradeEntity>> {
        TODO("Not yet implemented")
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

    override suspend fun getPeriods(
        schoolYear: String
    ): Result<List<PeriodEntity>> {
        val year = if (schoolYear.isEmpty()) getCurrentYearId().getOrNull().toString() else schoolYear
        return withContext(Dispatchers.IO) { gradesNetworkDataSource.get().getPeriods(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = year,
                classId = getClassId(year).getOrNull().toString()
            ).fold(
                onSuccess = { value -> periodMapper.get().invoke(value) },
                onFailure = { error ->
                Log.d("f",error.message.toString())
                    Result.failure(error)
                }
            )
        }
    }

    override suspend fun getSubjects(schoolYear: String): Result<List<SubjectEntity>> {
        return withContext(Dispatchers.IO) {
            val year = if (schoolYear.isEmpty()) getCurrentYearId().getOrNull().toString() else schoolYear
            gradesNetworkDataSource.get().getSubjects(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = year,
                classId = getClassId(year).getOrNull().toString(),
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