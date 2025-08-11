package com.vovka.egov66client.data.repo

import com.vovka.egov66client.data.mapper.StudentMapper
import com.vovka.egov66client.data.mapper.WeekScheduleMapper
import com.vovka.egov66client.data.source.AccountNetworkDataSource
import com.vovka.egov66client.data.source.ScheduleNetworkDataSource
import com.vovka.egov66client.data.source.StudentStorageDataSource
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import com.vovka.egov66client.domain.student.entity.StudentEntity
import com.vovka.egov66client.domain.student.repo.StudentRepository
import dagger.Lazy
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.net.CookieManager
import javax.inject.Inject

@Reusable
class StudentRepositoryImpl @Inject constructor(
    private val accountNetworkDataSource: AccountNetworkDataSource,
    private val studentStorageDataSource: Lazy<StudentStorageDataSource>,
    private val studentMapper: Lazy<StudentMapper>
): StudentRepository {
    override suspend fun getFirstStudent(): Result<StudentEntity> {
        return withContext(Dispatchers.IO) {
            val authToken = studentStorageDataSource.get().aiss2Auth.first()
            if (authToken == null) {
                return@withContext Result.failure(Exception("Auth token not found"))
            }
            accountNetworkDataSource.getStudents(
                Aiss2Auth = "Bearer $authToken"
            ).fold(
                onSuccess = { value -> studentMapper.get().invoke(value) },
                onFailure = { error -> Result.failure(error) }
            )
        }
    }

    override suspend fun updateAuthToken(token: String) {
        studentStorageDataSource.get().updateAiss2Auth(token)
    }

    override suspend fun updateStudentId() {
        withContext(Dispatchers.IO) {
            val authToken = studentStorageDataSource.get().aiss2Auth.first()
            if (authToken == null) {
                return@withContext
            }
            accountNetworkDataSource.getStudents(
                Aiss2Auth = "Bearer $authToken"
            ).fold(
                onSuccess = { value -> 
                    studentStorageDataSource.get().updateStudentId(value.students.first().id)
                },
                onFailure = { /* Ignore error */ }
            )
        }

    }

    override suspend fun logout(){
       studentStorageDataSource.get().updateAiss2Auth(null)

    }

    override suspend fun checkAuthToken(): Boolean {
        return !studentStorageDataSource.get().aiss2Auth.first().isNullOrEmpty()
    }

}