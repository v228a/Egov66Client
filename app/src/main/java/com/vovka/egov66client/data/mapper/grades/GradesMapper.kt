package com.vovka.egov66client.data.mapper.grades

import android.os.Build
import androidx.annotation.RequiresApi
import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.GradesEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodGradeEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodLessonGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class GradesMapper @Inject constructor(
    private val yearGradesMapper: YearGradesMapper
) {
    @RequiresApi(Build.VERSION_CODES.O)//TODO fix
    operator fun invoke(model: GradesResponse): Result<GradesEntity> {
        android.util.Log.d("GradesMapper", "Обрабатываем ответ: weekGradesTable=${model.weekGradesTable != null}, yearGradesTable=${model.yearGradesTable != null}, periodGradesTable=${model.periodGradesTable != null}")
        return runCatching {
            when {
                model.weekGradesTable != null -> {
                    android.util.Log.d("GradesMapper", "Обрабатываем недельные оценки")
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
                    android.util.Log.d("GradesMapper", "Обрабатываем годовые оценки")
                    val yearGrades = yearGradesMapper.invoke(model).getOrNull() ?: emptyList()
                    
                    GradesEntity(
                        periodGrades = null,
                        yearGrades = yearGrades,
                        weekGrades = null
                    )
                }

                model.periodGradesTable != null && model.periodGradesTable.disciplines.isNotEmpty() -> {
                    android.util.Log.d("GradesMapper", "Обрабатываем периодические оценки: ${model.periodGradesTable.disciplines.size} дисциплин")
                    val periodTable = model.periodGradesTable
                    val periodGrades = periodTable.disciplines.map { discipline ->
                        PeriodGradeEntity(
                            name = discipline.name,
                            averageGrade = discipline.averageGrade,
                            averageWeightedGrade = discipline.averageWeightedGrade,
                            totalGrade = discipline.totalGrade,
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
                    android.util.Log.d("GradesMapper", "Периодические оценки присутствуют, но дисциплины пустые")
                    GradesEntity(
                        periodGrades = emptyList(),
                        yearGrades = null,
                        weekGrades = null
                    )
                }

                else -> {
                    android.util.Log.d("GradesMapper", "Все таблицы null или пустые")
                    android.util.Log.d("GradesMapper", "weekGradesTable=${model.weekGradesTable}, yearGradesTable=${model.yearGradesTable}, periodGradesTable=${model.periodGradesTable}")
                    error("Все таблицы null")
                }
            }
        }
    }
}