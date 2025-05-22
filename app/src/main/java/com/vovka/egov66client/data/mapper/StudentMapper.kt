package com.vovka.egov66client.data.mapper

import com.vovka.egov66client.data.dto.student.StudentResponse
import com.vovka.egov66client.domain.student.entity.StudentEntity
import javax.inject.Inject

class StudentMapper @Inject constructor() {
    operator fun invoke(model: StudentResponse): Result<StudentEntity> {
        val student = model.students.first()
        return kotlin.runCatching {
            StudentEntity(
                firstName = student.firstName,
                lastName = student.lastName,
                surName = student.surName,
                className = student.className,
                orgName = student.orgName,
                id = student.id,
                avatarId = student.avatarId
            )
        }
    }
}