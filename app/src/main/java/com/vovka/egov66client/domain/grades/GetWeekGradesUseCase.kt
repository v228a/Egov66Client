package com.vovka.egov66client.domain.grades

import com.vovka.egov66client.data.dto.grades.school.SchoolYear
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

class GetWeekGradesUseCase @Inject constructor(
    private val repo: GradesRepository

) {
    suspend operator fun invoke(
        periodId: String,
        subjectId: String,
        weekNumber: Int?,
        schoolYearId: String
    ): Result<List<GradeWeekEntity>> {
        return repo.getWeekGrades(
            periodId = periodId,
            subjectId = subjectId,
            weekNumber = weekNumber,
            schoolYearId = schoolYearId
        )
    }
}