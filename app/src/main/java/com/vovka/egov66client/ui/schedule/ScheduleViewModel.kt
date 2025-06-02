package com.vovka.egov66client.ui.schedule

import androidx.lifecycle.ViewModel
import com.vovka.egov66client.domain.schedule.GetScheduleCurrentWeekUseCase
import com.vovka.egov66client.domain.schedule.GetScheduleWeekUseCase
import com.vovka.egov66client.domain.student.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    val getScheduleWeekUseCase: GetScheduleWeekUseCase,
    val getScheduleCurrentWeekUseCase: GetScheduleCurrentWeekUseCase,
    val logoutUseCase: LogoutUseCase
) : ViewModel()