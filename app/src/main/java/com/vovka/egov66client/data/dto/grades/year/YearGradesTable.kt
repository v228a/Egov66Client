package com.vovka.egov66client.data.dto.grades.year

import com.google.gson.annotations.SerializedName


data class YearGradesTable(
    @SerializedName("lessonGrades") val lessonGrades: List<YearLessonGrade>,
    @SerializedName("periods") val periods: List<Period>
)
