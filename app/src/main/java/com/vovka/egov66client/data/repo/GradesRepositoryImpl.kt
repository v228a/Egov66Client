package com.vovka.egov66client.data.repo

import com.vovka.egov66client.data.mapper.HomeWorkMapper
import com.vovka.egov66client.data.mapper.grades.YearMapper
import com.vovka.egov66client.data.source.GradesNetworkDataSource
import com.vovka.egov66client.data.source.HomeWorkNetworkDataSource
import com.vovka.egov66client.data.source.StudentStorageDataSource
import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.SubjectEntity
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import dagger.Lazy
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@Reusable
class GradesRepositoryImpl @Inject constructor(
    private val gradesNetworkDataSource: Lazy<GradesNetworkDataSource>,
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val yearMapper: Lazy<YearMapper>
): GradesRepository {


    override suspend fun getYears(): Result<List<YearsEntity>> {
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getYears(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
            ).fold(
                onSuccess = { value -> yearMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun getPeriods(): Result<List<PeriodEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjects(): Result<List<SubjectEntity>> {
        TODO("Not yet implemented")
    }


}