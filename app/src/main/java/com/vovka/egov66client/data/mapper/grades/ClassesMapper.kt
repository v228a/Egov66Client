package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.ClassesResponse
import com.vovka.egov66client.data.dto.grades.PeriodsResponse
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import javax.inject.Inject

class ClassesMapper @Inject constructor() {
    operator fun invoke(model: ClassesResponse): Result<String> {
        return runCatching {
           model.currentClass.id
        }
    }
}