package com.vovka.egov66client.data.dto.grades.period

import com.google.gson.annotations.SerializedName

data class PeriodGradesTable(
    @SerializedName("days")
    val days: List<Day>,

    @SerializedName("disciplines")
    val disciplines: List<Discipline>
)