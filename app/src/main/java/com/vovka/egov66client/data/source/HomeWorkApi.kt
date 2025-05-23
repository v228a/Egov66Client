package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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
}