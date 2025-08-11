package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.SchoolYearResponse
import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkFilesEntity
import javax.inject.Inject

class YearMapper @Inject constructor() {
    operator fun invoke(model: SchoolYearResponse): Result<List<YearsEntity>> {
        return runCatching {
            model.schoolYears.map {
                YearsEntity(it.id,it.text)
            }
        }
    }
}