package com.vovka.egov66client.data.source


import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject


@Reusable
class HomeWorkNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api by lazy {
        retrofit.create(HomeWorkApi::class.java)
    }

    suspend fun getStudents(Aiss2Auth: String, studentId: String, date: String): Result<HomeWorkResponse> {
        return kotlin.runCatching { api.getHomeWork(
            Aiss2Auth = Aiss2Auth,
            studentId = studentId,
            date = date
        ) }
    }
}