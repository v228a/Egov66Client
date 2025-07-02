package com.vovka.egov66client.data.repo

import android.util.Log
import com.vovka.egov66client.data.mapper.HomeWorkMapper
import com.vovka.egov66client.data.mapper.grades.PeriodMapper
import com.vovka.egov66client.data.mapper.grades.SubjectsMapper
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
    private val yearMapper: Lazy<YearMapper>,
    private val subjectsMapper: Lazy<SubjectsMapper>,
    private val periodMapper: Lazy<PeriodMapper>
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
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getPeriods(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = "2024"
            ).fold(
                onSuccess = { value -> periodMapper.get().invoke(value) },
                onFailure = { error ->
                Log.d("f",error.message.toString())
                    Result.failure(error)
                }
            )
        }
    }

    override suspend fun getSubjects(): Result<List<SubjectEntity>> {
        return withContext(Dispatchers.IO) {
            gradesNetworkDataSource.get().getSubjects(
                Aiss2Auth = "Bearer " + studentStorageDataSource.get().aiss2Auth.first().toString(),
                studentId = studentStorageDataSource.get().studentId.first().toString(),
                schoolYear = "2024"
            ).fold(
                onSuccess = { value -> subjectsMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }


}