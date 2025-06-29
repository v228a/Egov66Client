package com.vovka.egov66client.data.dto.grades.week

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.PaginationData

data class WeekGradesTable(
    @SerializedName("paginationData")  val pagination: PaginationData,
    @SerializedName("beginDate")  val beginDate: String,
    @SerializedName("endDate")  val endDate: String,
    @SerializedName("days") val days: List<DayGrades>
)
