package com.vovka.egov66client.data.dto.grades.year

import com.google.gson.annotations.SerializedName

data class YearLessonGrade(
    @SerializedName("lesson") val lesson: Lesson,
    @SerializedName("yearGrade") val yearGrade: String?,
    @SerializedName("testGrade") val testGrade: String?,
    @SerializedName("finalyGrade") val finalGrade: String?,
    @SerializedName("grades") val periodGrades: List<PeriodGrade>
)
