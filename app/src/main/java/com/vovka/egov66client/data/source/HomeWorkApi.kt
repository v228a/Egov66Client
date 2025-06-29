package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import com.vovka.egov66client.data.dto.homework.HomeworkDone
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface HomeWorkApi {
    @GET("api/homework")
    suspend fun getHomeWork(
        @Header("Authorization") Aiss2Auth: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br",
        @Query("studentId") studentId: String,
        @Query("date") date: String,
    ) : HomeWorkResponse

    @POST("api/homework/done")
    suspend fun doneHomeWork(
        @Header("Authorization") Aiss2Auth: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Body data: HomeworkDone,
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br",
    )



    @GET("/api/lesson/homework/files")
    @Streaming
    suspend fun downloadHomeWorkFile(
        @Header("Authorization") Aiss2Auth: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br",
    ) : ResponseBody
}