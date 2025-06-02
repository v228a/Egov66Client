package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.schedule.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ScheduleApi {
    @GET("/api/schedule")
    suspend fun getSchedule(
        @Header("Authorization") Aiss2Auth: String,
        @Query("studentId") studentId: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): ScheduleResponse

    @GET("/api/schedule")
    suspend fun getScheduleOnCurrentWeek(
        @Header("Authorization") Aiss2Auth: String,
        @Query("studentId") studentId: String,
        @Query("pageNumber") pageNumber: Int,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): ScheduleResponse
}