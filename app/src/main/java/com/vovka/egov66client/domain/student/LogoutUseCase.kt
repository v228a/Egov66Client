package com.vovka.egov66client.domain.student

import com.vovka.egov66client.domain.student.repo.StudentRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: StudentRepository,
) {
    suspend operator fun invoke() {
        repo.logout()
    }
}