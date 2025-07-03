package com.vovka.egov66client.domain.grades.repo

import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity

interface GradesRepository {
    suspend fun getYears() : Result<List<YearsEntity>>
    suspend fun getPeriods() : Result<List<PeriodEntity>>
    suspend fun getSubjects() : Result<List<SubjectEntity>>
    suspend fun getCurrentYear() : Result<String>
}