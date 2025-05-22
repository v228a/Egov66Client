package com.vovka.egov66client.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("schoolYear") val schoolYear: String,
    @SerializedName("paginationData") val paginationData: PaginationData,
    @SerializedName("scheduleModel") val scheduleModel: ScheduleModel
)
