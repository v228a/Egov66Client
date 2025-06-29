package com.vovka.egov66client.data.source


import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import com.vovka.egov66client.data.dto.homework.HomeworkDone
import dagger.Reusable
import retrofit2.Retrofit
import java.io.File
import javax.inject.Inject


@Reusable
class HomeWorkNetworkDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api by lazy {
        retrofit.create(HomeWorkApi::class.java)
    }

    suspend fun getHomework(Aiss2Auth: String, studentId: String, date: String): Result<HomeWorkResponse> {
        return kotlin.runCatching { api.getHomeWork(
            Aiss2Auth = Aiss2Auth,
            studentId = studentId,
            date = date
        ) }
    }

    suspend fun setHomeworkDone(Aiss2Auth: String, studentId: String, homeworkId: String,isDone: Boolean): Result<Unit>{
        return runCatching {
            api.doneHomeWork(
                Aiss2Auth = Aiss2Auth,
                data = HomeworkDone(
                    homeworkId = homeworkId,
                    isDone = isDone,
                    studentId = studentId
                )
            )
        }
    }

    suspend fun downloadHomeWork(Aiss2Auth: String,FileID: String){

    }
}