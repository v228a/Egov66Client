package com.vovka.egov66client.domain.grades.repo

import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity

interface GradesRepository {
    suspend fun getYears() : Result<List<YearsEntity>>
    suspend fun getPeriods(
        schoolYear: String = ""
    ) : Result<List<PeriodEntity>>
    suspend fun getSubjects(schoolYear: String = "") : Result<List<SubjectEntity>>
    suspend fun getCurrentYearText() : Result<String>
    suspend fun getCurrentYearId() : Result<String>
    suspend fun getWeekGrades(
        periodId: String,
        subjectId: String,
        weekNumber: Int?,
        schoolYearId: String
    ): Result<List<GradeWeekEntity>>
    suspend fun getClassId() : Result<String>
}