package com.vovka.egov66client.data.dto.grades

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.grades.period.PeriodGradesTable
import com.vovka.egov66client.data.dto.grades.week.WeekGradesTable
import com.vovka.egov66client.data.dto.grades.year.YearGradesTable

data class GradesResponse(
    @SerializedName("yearGradesTable")
    val yearGradesTable: YearGradesTable?,

    @SerializedName("periodGradesTable")
    val periodGradesTable: PeriodGradesTable,

    @SerializedName("weekGradesTable")
    val weekGradesTable: WeekGradesTable?,

    @SerializedName("showAverageWeighted")
    val showAverageWeighted: Boolean
)