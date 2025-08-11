package com.vovka.egov66client.domain.grades

import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

class GetSchoolYearsUseCase @Inject constructor(
    private val repo: GradesRepository

) {
    suspend operator fun invoke(): Result<List<YearsEntity>> {
        return repo.getYears()
    }
}