package com.vovka.egov66client.domain.homework

import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.repo.HomeWorkRepository
import java.time.LocalDateTime
import javax.inject.Inject


class GetHomeWorkUseCase @Inject constructor(
    private val repo: HomeWorkRepository
) {
    suspend operator fun invoke(date: LocalDateTime): Result<DayHomeWorkEntity> {
        return repo.getHomework(date)
    }
}