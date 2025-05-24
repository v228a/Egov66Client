package com.vovka.egov66client.domain.schedule

import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import javax.inject.Inject

class GetScheduleCurrentWeekUseCase @Inject constructor(
    private val repo: ScheduleRepository
//    private val getLoginUseCase: GetLoginUseCase,
) {
    suspend operator fun invoke(weekNumber: Int):  Result<WeekScheduleEntity> {
        return repo.getWeekNumberSchedule(weekNumber)
    }
}