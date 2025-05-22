package com.vovka.egov66client.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleModel(
    @SerializedName("beginDate") val beginDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("weekNumber") val weekNumber: Int,
    @SerializedName("days") val days: List<Day>
)