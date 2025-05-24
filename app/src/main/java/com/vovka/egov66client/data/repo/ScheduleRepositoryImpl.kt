package com.vovka.egov66client.data.repo

import android.util.Log
import com.vovka.egov66client.data.mapper.WeekScheduleMapper
import com.vovka.egov66client.data.source.AccountNetworkDataSource
import com.vovka.egov66client.data.source.StudentStorageDataSource
import com.vovka.egov66client.data.source.ScheduleNetworkDataSource
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import dagger.Lazy
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleNetworkDataSource: ScheduleNetworkDataSource,
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val weekScheduleMapper: Lazy<WeekScheduleMapper>
): ScheduleRepository {
    override suspend fun getCurrentSchedule(): Result<WeekScheduleEntity> {
        return withContext(Dispatchers.IO) {
            val authToken = studentStorageDataSource.get().aiss2Auth.first()
            if (authToken == null) {
                return@withContext Result.failure(Exception("Auth token not found"))
            }
            val studentId = studentStorageDataSource.get().studentId.first()
            if (studentId == null) {
                return@withContext Result.failure(Exception("StudentId not found"))
            }

            scheduleNetworkDataSource.getSchedule(
                Aiss2Auth = "Bearer $authToken",
                StudentId = studentId
            ).fold(
                onSuccess = { value -> weekScheduleMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

}