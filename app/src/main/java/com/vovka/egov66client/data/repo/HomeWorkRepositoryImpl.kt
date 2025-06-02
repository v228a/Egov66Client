package com.vovka.egov66client.data.repo


import android.util.Printer
import com.vovka.egov66client.data.mapper.HomeWorkMapper
import com.vovka.egov66client.data.mapper.StudentMapper
import com.vovka.egov66client.data.mapper.WeekScheduleMapper
import com.vovka.egov66client.data.source.AccountNetworkDataSource
import com.vovka.egov66client.data.source.HomeWorkNetworkDataSource
import com.vovka.egov66client.data.source.ScheduleNetworkDataSource
import com.vovka.egov66client.data.source.StudentStorageDataSource
import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import com.vovka.egov66client.domain.homework.repo.HomeWorkRepository
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import com.vovka.egov66client.domain.student.entity.StudentEntity
import com.vovka.egov66client.domain.student.repo.StudentRepository
import dagger.Lazy
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject



@Reusable
class HomeWorkRepositoryImpl @Inject constructor(
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val homeWorkNetworkDataSource: Lazy<HomeWorkNetworkDataSource>,
    private val homeWorkMapper: Lazy<HomeWorkMapper>
): HomeWorkRepository {
    override suspend fun getHomework(date: LocalDateTime): Result<DayHomeWorkEntity> {
        return withContext(Dispatchers.IO) {
            homeWorkNetworkDataSource.get().getHomework(
            Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
            studentId = studentStorageDataSource.get().studentId.first().toString(),
            date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        ).fold(
            onSuccess = { value -> homeWorkMapper.get().invoke(value) },
            onFailure = { error -> Result.failure(error) }
            )
        }
    }
}

