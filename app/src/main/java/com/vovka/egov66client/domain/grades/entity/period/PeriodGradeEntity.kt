package com.vovka.egov66client.domain.grades.entity.period

class PeriodGradeEntity(
    val name: String,
    val averageGrade: Double?,
    val averageWeightedGrade: Double?,
    val totalGrade: String,
    val grades: List<PeriodLessonGradeEntity>
)