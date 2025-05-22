package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.student.StudentResponse
import com.vovka.egov66client.data.dto.student.Student
import com.vovka.egov66client.data.dto.student.User
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject

@Reusable
class AccountNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api by lazy {
        retrofit.create(StudentApi::class.java)
    }

    suspend fun getStudents(Aiss2Auth: String): Result<StudentResponse> {
        return kotlin.runCatching { api.getStudents(Aiss2Auth) }
    }
}