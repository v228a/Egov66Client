package com.vovka.egov66client.domain.schedule.repo

import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity

interface ScheduleRepository {
    suspend fun getCurrentSchedule() : Result<WeekScheduleEntity>
}