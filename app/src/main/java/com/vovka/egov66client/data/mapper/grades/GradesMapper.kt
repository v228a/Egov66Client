package com.vovka.egov66client.data.mapper.grades

import android.os.Build
import androidx.annotation.RequiresApi
import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.domain.grades.entity.week.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.week.WeekGradesListEntity
import com.vovka.egov66client.domain.grades.entity.week.PageWeekDataEntity
import com.vovka.egov66client.domain.grades.entity.GradesEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodGradeEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodLessonGradeEntity
import java.time.ZoneId
import javax.inject.Inject

class GradesMapper @Inject constructor(
    private val yearGradesMapper: YearGradesMapper
) {
    @RequiresApi(Build.VERSION_CODES.O)//TODO fix
    operator fun invoke(model: GradesResponse): Result<GradesEntity> {
        return runCatching {
            when {
                model.weekGradesTable != null -> {
                    val weekTable = model.weekGradesTable
                    val weekGradesList = weekTable.days.flatMap { day ->
                        day.lessons.map { lesson ->
                            val grade = lesson.grades.firstOrNull()?.firstOrNull() ?: ""
                            val time = String.format(
                                "%02d:%02d-%02d:%02d",
                                lesson.beginHour, lesson.beginMinute,
                                lesson.endHour, lesson.endMinute
                            )
                            WeekGradesListEntity(
                                id = lesson.id,
                                grade = grade,
                                lesson = lesson.name,
                                lessonNumber = lesson.sequenceNumber.toString(),
                                time = time
                            )
                        }
                    }

                    val pagination = PageWeekDataEntity(
                        pageSize = weekTable.pagination.pageSize,
                        pageNumber = weekTable.pagination.pageNumber,
                        totalCount = weekTable.pagination.totalCount,
                        pageActionLink = weekTable.pagination.pageActionLink,
                        totalPages = weekTable.pagination.totalPages,
                        hasPreviousPage = weekTable.pagination.hasPreviousPage,
                        hasNextPage = weekTable.pagination.hasNextPage,
                        pageNumberOutOfRange = weekTable.pagination.pageNumberOutOfRange
                    )

                    val weekGrades = GradeWeekEntity(
                        grades = weekGradesList,
                        pagination = pagination
                    )

                    GradesEntity(
                        periodGrades = null,
                        yearGrades = null,
                        weekGrades = weekGrades
                    )
                }

                model.yearGradesTable != null -> {
                    val yearGrades = yearGradesMapper.invoke(model).getOrNull() ?: emptyList()
                    GradesEntity(
                        periodGrades = null,
                        yearGrades = yearGrades,
                        weekGrades = null
                    )
                }

                model.periodGradesTable != null && model.periodGradesTable.disciplines.isNotEmpty() -> {
                    val periodTable = model.periodGradesTable
                    val periodGrades = periodTable.disciplines.map { discipline ->
                        PeriodGradeEntity(
                            name = discipline.name,
                            averageGrade = discipline.averageGrade,
                            averageWeightedGrade = discipline.averageWeightedGrade,
                            totalGrade = discipline.totalGrade ?: "—",
                            grades = discipline.grades.map { grade ->
                                PeriodLessonGradeEntity(
                                    presence = grade.presence,
                                    lessonId = grade.lessonId,
                                    date = grade.date.toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime(),
                                    grades = grade.grades
                                )
                            }
                        )
                    }

                    GradesEntity(
                        periodGrades = periodGrades,
                        yearGrades = null,
                        weekGrades = null
                    )
                }
                
                model.periodGradesTable != null && model.periodGradesTable.disciplines.isEmpty() -> {
                    GradesEntity(
                        periodGrades = emptyList(),
                        yearGrades = null,
                        weekGrades = null
                    )
                }

                else -> {
                    error("Все таблицы null")
                }
            }
        }
    }
}