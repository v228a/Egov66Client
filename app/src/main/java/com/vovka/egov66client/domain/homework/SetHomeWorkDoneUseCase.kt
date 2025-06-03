package com.vovka.egov66client.domain.homework

import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.repo.HomeWorkRepository
import java.time.LocalDateTime
import javax.inject.Inject

class SetHomeWorkDoneUseCase @Inject constructor(
    private val repo: HomeWorkRepository
) {
    suspend operator fun invoke(isDone: Boolean,homeworkId: String) {
        return repo.setHomeworkDone(
            isDone = isDone,
            homeworkId = homeworkId
        )
    }
}