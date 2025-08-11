package com.vovka.egov66client.domain.grades

import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject


class GetPeriodUseCase @Inject constructor(
    private val repo: GradesRepository

) {
    suspend operator fun invoke(schoolYear: String = ""): Result<List<PeriodEntity>> {
        return repo.getPeriods(
            schoolYear = schoolYear
        )
    }
}