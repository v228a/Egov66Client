package com.vovka.egov66client.domain.grades.entity

import com.vovka.egov66client.domain.grades.entity.period.PeriodGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity

class GradesEntity(
    val periodGrades: List<PeriodGradeEntity>?,//Переметить на grade
    val yearGrades: List<YearGradeEntity>?,
    val weekGrades: List<GradeWeekEntity>?
)