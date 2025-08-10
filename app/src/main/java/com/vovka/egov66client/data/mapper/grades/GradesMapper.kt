package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.GradesEntity
import javax.inject.Inject

class GradesMapper @Inject constructor() {
    operator fun invoke(model: GradesResponse): Result<GradesEntity> {
        return runCatching {
            when {
                model.weekGradesTable != null -> {
                    val weekTable = model.weekGradesTable
                    val weekGrades = weekTable.days.flatMap { day ->
                        day.lessons.map { lesson ->
                            val grade = lesson.grades.firstOrNull()?.firstOrNull() ?: ""
                            val time = String.format(
                                "%02d:%02d-%02d:%02d",
                                lesson.beginHour, lesson.beginMinute,
                                lesson.endHour, lesson.endMinute
                            )
                            GradeWeekEntity(
                                id = lesson.id,
                                grade = grade,
                                lesson = lesson.name,
                                lessonNumber = lesson.sequenceNumber.toString(),
                                time = time
                            )
                        }
                    }

                    GradesEntity(
                        periodGrades = null,
                        yearGrades = null,
                        weekGrades = weekGrades
                    )
                }

                model.yearGradesTable != null -> {
                    val yearTable = model.yearGradesTable
                    TODO()
//
//                    GradesEntity(
//                        periodGrades = null,
//                        yearGrades = yearGrades,
//                        weekGrades = null
//                    )
                }

                model.periodGradesTable != null -> {
                    val periodTable = model.periodGradesTable
                    TODO()
//
//                    GradesEntity(
//                        periodGrades = periodGrades,
//                        yearGrades = null,
//                        weekGrades = null
//                    )
                }

                else -> error("Все таблицы null")
            }
        }

    }
}