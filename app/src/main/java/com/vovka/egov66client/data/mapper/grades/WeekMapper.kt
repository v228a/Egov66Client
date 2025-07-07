package com.vovka.egov66client.data.mapper.grades

import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.data.dto.grades.SchoolYearResponse
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import javax.inject.Inject

class WeekMapper @Inject constructor() {
    operator fun invoke(model: GradesResponse): Result<List<GradeWeekEntity>> {
        return runCatching {
            val weekTable = model.weekGradesTable ?: return@runCatching emptyList()
            weekTable.days.flatMap { day ->
                day.lessons.map { lesson ->
                    val grade = lesson.grades.firstOrNull()?.firstOrNull() ?: ""
                    val time = String.format("%02d:%02d-%02d:%02d", lesson.beginHour, lesson.beginMinute, lesson.endHour, lesson.endMinute)
                    GradeWeekEntity(
                        id = lesson.id,
                        grade = grade,
                        lesson = lesson.name,
                        lessonNumber = lesson.sequenceNumber.toString(),
                        time = time
                    )
                }
            }
        }
    }
}