package com.vovka.egov66client.data.dto.grades.week

import com.google.gson.annotations.SerializedName

data class LessonGrades(
    @SerializedName("lessonId") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("sequenceNumber") val sequenceNumber: Int,
    @SerializedName("beginHour") val beginHour: Int,
    @SerializedName("beginMinute") val beginMinute: Int,
    @SerializedName("endHour") val endHour: Int,
    @SerializedName("endMinute") val endMinute: Int,
    @SerializedName("presence") val presence: String,
    @SerializedName("grades") val grades: List<List<String>>
)