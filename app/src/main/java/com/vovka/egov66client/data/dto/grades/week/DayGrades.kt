package com.vovka.egov66client.data.dto.grades.week

import com.google.gson.annotations.SerializedName

data class DayGrades(
    @SerializedName("date") val date: String,
    @SerializedName("lessonGrades") val lessons: List<LessonGrades>
)