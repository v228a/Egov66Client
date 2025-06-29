package com.vovka.egov66client.data.dto.grades

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.grades.year.Period

data class PeriodsResponse(
    @SerializedName("periods") val periods: List<Period>
)