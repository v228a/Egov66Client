package com.vovka.egov66client.data.dto.grades

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.grades.school.CurrentYear
import com.vovka.egov66client.data.dto.grades.school.SchoolYear

data class SchoolYearResponse(
    @SerializedName("currentYear") val currentYear: CurrentYear,
    @SerializedName("schoolYears") val schoolYears: List<SchoolYear>
)