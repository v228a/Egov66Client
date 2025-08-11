package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.PeriodsResponse
import com.vovka.egov66client.data.dto.grades.SchoolYearResponse
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import javax.inject.Inject

class PeriodMapper @Inject constructor() {
    operator fun invoke(model: PeriodsResponse): Result<List<PeriodEntity>> {
        return runCatching {
            model.periods.map {
                PeriodEntity(it.id,it.name)
            }
        }
    }
}