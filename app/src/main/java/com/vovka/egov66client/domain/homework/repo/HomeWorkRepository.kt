package com.vovka.egov66client.domain.homework.repo

import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import java.time.LocalDate
import java.time.LocalDateTime

interface HomeWorkRepository {
    suspend fun getHomework(date: LocalDateTime) : Result<DayHomeWorkEntity>
    suspend fun setHomeworkDone(isDone: Boolean,homeworkId: String)
}