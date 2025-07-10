package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.grades.ClassesResponse
import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.data.dto.grades.PeriodsResponse
import com.vovka.egov66client.data.dto.grades.SchoolYearResponse
import com.vovka.egov66client.data.dto.grades.SubjectResponse
import com.vovka.egov66client.data.dto.student.StudentResponse
import dagger.Reusable
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@Reusable
class GradesNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api by lazy {
        retrofit.create(GradesApi::class.java)
    }

    suspend fun getYears(Aiss2Auth: String, studentId: String): Result<SchoolYearResponse> {
        return runCatching { api.getYears(Aiss2Auth, studentId) }
    }

    suspend fun getWeekGrades(
        Aiss2Auth: String,
        schoolYear: String,
        periodId: String,
        subjectId: String,
        studentId: String,
        weekNumber: Int?,
        classId: String
    ) : Result<GradesResponse>{
        return runCatching {
            api.getWeekGrades(
                Aiss2Auth = Aiss2Auth,
                schoolYear = schoolYear,
                periodId = periodId,
                subjectId = subjectId,
                studentId   = studentId,
                weekNumber = weekNumber,
                classId = classId
            )
        }
    }

    suspend fun getSubjects(Aiss2Auth: String, studentId: String,schoolYear: String): Result<SubjectResponse> {
        return runCatching { api.getSubjects(
            Aiss2Auth = Aiss2Auth,
            studentId = studentId,
            schoolYear = schoolYear
        ) }
    }

    suspend fun getClasses(Aiss2Auth: String,schoolYear: String,studentId: String): Result<ClassesResponse>{
        return runCatching{
            api.getClasses(
                Aiss2Auth = Aiss2Auth,
                schoolYear = schoolYear,
                studentId = studentId
            )
        }
    }

    suspend fun getPeriods(Aiss2Auth: String, studentId: String,schoolYear: String): Result<PeriodsResponse> {
        return runCatching { api.getPeriods(
            Aiss2Auth = Aiss2Auth,
            studentId = studentId,
            schoolYear = schoolYear
        )  }
    }


//    suspend fun getClassId(Aiss2Auth: String, studentId: String,schoolYear: String):
}