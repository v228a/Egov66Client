package com.vovka.egov66client.domain.student

import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import com.vovka.egov66client.domain.student.entity.StudentEntity
import com.vovka.egov66client.domain.student.repo.StudentRepository
import javax.inject.Inject

class GetStudentInfoUseCase @Inject constructor(
    private val repo: StudentRepository,
) {
    suspend operator fun invoke():  Result<StudentEntity> {
        return repo.getFirstStudent()

    }
}