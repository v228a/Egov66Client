package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.grades.ClassesResponse
import com.vovka.egov66client.data.dto.grades.GradesResponse
import com.vovka.egov66client.data.dto.grades.PeriodsResponse
import com.vovka.egov66client.data.dto.grades.SchoolYearResponse
import com.vovka.egov66client.data.dto.grades.SubjectResponse
import com.vovka.egov66client.data.dto.grades.classes.ClassInfo
import com.vovka.egov66client.data.dto.schedule.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GradesApi {


    @GET("/api/estimate/years")
    suspend fun getYears(
        @Header("Authorization") Aiss2Auth: String,
        @Query("studentId") studentId: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): SchoolYearResponse

    @GET("/api/estimate")
    suspend fun getGrades(
        @Header("Authorization") Aiss2Auth: String,
        @Query("schoolYear") schoolYear: String,
        @Query("periodId") periodId: String,
        @Query("subjectId") subjectId: String,
        @Query("studentId") studentId: String,
        @Query("classId") classId: String,
        @Query("weekNumber") weekNumber: Int?,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): GradesResponse


    @GET("/api/classes")
    suspend fun getClasses(
        @Header("Authorization") Aiss2Auth: String,
        @Query("schoolYear") schoolYear: String,
        @Query("studentId") studentId: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ) : ClassesResponse

    @GET("/api/estimate/periods")
    suspend fun getPeriods(
        @Header("Authorization") Aiss2Auth: String,
        @Query("studentId") studentId: String,
        @Query("schoolYear") schoolYear: String,
        @Query("classId") classId: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): PeriodsResponse

    @GET("/api/estimate/subjects")
    suspend fun getSubjects(
        @Header("Authorization") Aiss2Auth: String,
        @Query("studentId") studentId: String,
        @Query("schoolYear") schoolYear: String,
        @Query("classId") classId: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ): SubjectResponse

}