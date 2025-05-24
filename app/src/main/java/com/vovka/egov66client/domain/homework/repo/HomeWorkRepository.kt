package com.vovka.egov66client.domain.homework.repo

import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity

interface HomeWorkRepository {
    suspend fun getHomework() : DayHomeWorkEntity
}