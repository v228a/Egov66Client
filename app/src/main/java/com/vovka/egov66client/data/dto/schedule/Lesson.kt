package com.vovka.egov66client.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("id") val id: String,
    @SerializedName("lessonid") val lessonId: String,
    @SerializedName("lessonName") val lessonName: String,
    @SerializedName("groupName") val groupName: String?,
    @SerializedName("room") val room: String?,
    @SerializedName("number") val number: Int,
    @SerializedName("beginHour") val beginHour: Int?,
    @SerializedName("beginMinute") val beginMinute: Int?,
    @SerializedName("endHour") val endHour: Int?,
    @SerializedName("endMinute") val endMinute: Int?
)