package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.SubjectResponse
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import javax.inject.Inject

class SubjectsMapper @Inject constructor() {
    operator fun invoke(model: SubjectResponse): Result<List<SubjectEntity>> {
        return runCatching {
            model.subjects.map {
                SubjectEntity(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }
}