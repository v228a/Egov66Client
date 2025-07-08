package com.vovka.egov66client.domain.student.repo

import com.vovka.egov66client.domain.student.entity.StudentEntity

interface StudentRepository {
    suspend fun getFirstStudent() : Result<StudentEntity>
    suspend fun updateAuthToken(token: String)
    suspend fun updateStudentId()
    suspend fun logout()
    suspend fun checkAuthToken(): Boolean
}