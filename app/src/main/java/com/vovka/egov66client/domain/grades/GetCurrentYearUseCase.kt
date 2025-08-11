package com.vovka.egov66client.domain.grades

import android.util.Log
import com.vovka.egov66client.domain.grades.entity.YearsEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

//Нужен для правильной выставки периода в дропдауне оценок
class GetCurrentYearUseCase @Inject constructor(
    private val repo: GradesRepository
) {
    suspend operator fun invoke(): Result<YearsEntity> {
        val id = repo.getCurrentYearId().getOrNull().toString()
        val name = repo.getCurrentYearText().getOrNull().toString()
        Log.d("F", id+" "+name)
        return runCatching {YearsEntity(id,name)}
    }
}