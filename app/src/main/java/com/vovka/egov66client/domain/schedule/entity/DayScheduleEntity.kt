package com.vovka.egov66client.domain.schedule.entity

class DayScheduleEntity (
    val date: String,
    val isCelebration: Boolean,
    val isWeekend: Boolean,
    val lessons: List<LessonEntity>
)