package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearPeriodGradeEntity
import javax.inject.Inject

class YearGradesMapper @Inject constructor() {
    operator fun invoke(model: GradesResponse): Result<List<YearGradeEntity>> {
        return runCatching {
            model.yearGradesTable?.let { yearGradesTable ->
                yearGradesTable.lessonGrades.map { lessonGrade ->
                    YearGradeEntity(
                        subjectName = lessonGrade.lesson.name,
                        periodGrades = lessonGrade.periodGrades.map { periodGrade ->
                            YearPeriodGradeEntity(
                                periodName = yearGradesTable.periods.find { it.id == periodGrade.periodId }?.name ?: "",
                                grade = periodGrade.averageGrade,
                                rank = periodGrade.averageWeightedGrade?.toInt()
                            )
                        },
                        yearGrade = lessonGrade.yearGrade?.toIntOrNull() ?: 0,
                        examGrade = lessonGrade.testGrade?.toIntOrNull(),
                        finalGrade = lessonGrade.finalGrade?.toIntOrNull()
                    )
                }
            } ?: emptyList()
        }
    }
}