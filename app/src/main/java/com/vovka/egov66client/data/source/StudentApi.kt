package com.vovka.egov66client.data.source

import com.vovka.egov66client.data.dto.student.StudentResponse
import retrofit2.http.GET
import retrofit2.http.Header


//TODO вынести хидеры куданибудь
interface StudentApi {
    @GET("api/students")
    suspend fun getStudents(
        @Header("Authorization") Aiss2Auth: String,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0",
        @Header("Accept") accept: String = "*/*",
        @Header("Accept-Encoding") acceptEncoding: String = "gzip, deflate, br"
    ) : StudentResponse
}


