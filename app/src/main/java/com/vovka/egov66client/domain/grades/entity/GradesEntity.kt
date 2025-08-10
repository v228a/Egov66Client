package com.vovka.egov66client.domain.grades.entity

import com.vovka.egov66client.data.dto.grades.year.PeriodGrade
import com.vovka.egov66client.domain.grades.entity.period.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity

class GradesEntity(
    val periodGrades: List<PeriodEntity>?,//Переметить на grade
    val yearGrades: List<YearGradeEntity>?,
    val weekGrades: List<GradeWeekEntity>?
)