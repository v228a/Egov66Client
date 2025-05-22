package com.vovka.egov66client.domain.schedule.entity

class WeekScheduleEntity (
    val weekNumber: Int,
    val beginDate: String,
    val endDate: String,
    val schedule: List<DayScheduleEntity>
)