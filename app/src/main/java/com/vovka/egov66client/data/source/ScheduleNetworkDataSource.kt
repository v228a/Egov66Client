package com.vovka.egov66client.data.source

import android.accounts.NetworkErrorException
import com.vovka.egov66client.data.dto.schedule.ScheduleResponse
import com.vovka.egov66client.data.dto.schedule.mockdata
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject

@Reusable
class ScheduleNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api by lazy {
        retrofit.create(ScheduleApi::class.java)
    }

    suspend fun getSchedule(Aiss2Auth: String, StudentId: String): Result<ScheduleResponse> {
        return runCatching {api.getSchedule(Aiss2Auth,StudentId)}
    }
    suspend fun getScheduleOnCurrentWeek(Aiss2Auth: String, StudentId: String,weekNumber: Int): Result<ScheduleResponse>{
        return runCatching {api.getScheduleOnCurrentWeek(Aiss2Auth,StudentId,weekNumber)}
    }

}