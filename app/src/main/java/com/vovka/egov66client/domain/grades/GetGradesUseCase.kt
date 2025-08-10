package com.vovka.egov66client.domain.grades

import android.util.Log
import com.vovka.egov66client.domain.grades.entity.GradesEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(
    private val repo: GradesRepository
) {
    suspend operator fun invoke(
        periodId:String,
        subjectId:String,
        weekNumber:Int?,
        schoolYearId:String
    ): Result<GradesEntity> {
       return repo.getGrades(
           periodId = periodId,
           subjectId = subjectId,
           weekNumber = weekNumber,
           schoolYearId = schoolYearId
       )
    }
}