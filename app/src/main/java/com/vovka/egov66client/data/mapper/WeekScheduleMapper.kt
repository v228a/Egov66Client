package com.vovka.egov66client.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.vovka.egov66client.data.dto.schedule.ScheduleResponse
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.domain.schedule.entity.LessonEntity
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class WeekScheduleMapper @Inject constructor() {

    operator fun invoke(model: ScheduleResponse): Result<WeekScheduleEntity> {
        return kotlin.runCatching {
            val scheduleModel = model.scheduleModel
            val daySchedules = scheduleModel.days.map { day ->
                val lessons = day.lessons.map { lesson ->
                    val time = formatTime(
                        lesson.beginHour,
                        lesson.beginMinute,
                        lesson.endHour,
                        lesson.endMinute
                    )
                    val lessonText = buildString {
                        append(lesson.lessonName)
                    }

                    val room = buildString { append(lesson.room) }
                    LessonEntity(time, lessonText, room )
                }
                DayScheduleEntity(
                    lessons = lessons,
                    date = formatDate(day.date),
                    isCelebration = day.isCelebration,
                    isWeekend = day.isWeekend

                )
            }

            WeekScheduleEntity(
                weekNumber = scheduleModel.weekNumber,
                beginDate = formatDate(scheduleModel.beginDate),
                endDate = formatDate(scheduleModel.endDate),
                schedule = daySchedules
            )
        }
    }

    private fun formatTime(
        beginHour: Int?,
        beginMinute: Int?,
        endHour: Int?,
        endMinute: Int?
    ): String {
        val beginTime = formatTimePart(beginHour, beginMinute)
        val endTime = formatTimePart(endHour, endMinute)
        return "$beginTime-$endTime"
    }

    private fun formatTimePart(hour: Int?, minute: Int?): String {
        val h = hour?.toString()?.padStart(2, '0') ?: "00"
        val m = minute?.toString()?.padStart(2, '0') ?: "00"
        return "$h:$m"
    }

    private fun formatDate(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = inputFormat.parse(isoDate)
        return outputFormat.format(date)
    }
}