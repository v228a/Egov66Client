package com.vovka.egov66client.domain.schedule

import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.domain.schedule.entity.LessonEntity
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import javax.inject.Inject

class GetScheduleWeekUseCase @Inject constructor(
    private val repo: ScheduleRepository
//    private val getLoginUseCase: GetLoginUseCase,
) {
    suspend operator fun invoke():  Result<WeekScheduleEntity> {
        return repo.getCurrentSchedule()
    }
}