package com.vovka.egov66client.domain.grades

import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val repo: GradesRepository

) {
    suspend operator fun invoke(): Result<List<SubjectEntity>> {
        return repo.getSubjects()
    }
}