package com.vovka.egov66client.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("date") val date: String,
    @SerializedName("isWeekend") val isWeekend: Boolean,
    @SerializedName("isCelebration") val isCelebration: Boolean,
    @SerializedName("dayOfWeekName") val dayOfWeekName: String,
    @SerializedName("scheduleDayLessonModels") val lessons: List<Lesson>
)