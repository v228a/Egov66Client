package com.vovka.egov66client.data.mapper

import com.vovka.egov66client.data.dto.homework.HomeWorkResponse
import com.vovka.egov66client.data.dto.student.StudentResponse
import com.vovka.egov66client.domain.homework.entity.DayHomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import com.vovka.egov66client.domain.student.entity.StudentEntity
import javax.inject.Inject

class HomeWorkMapper @Inject constructor() {
    operator fun invoke(model: HomeWorkResponse): Result<DayHomeWorkEntity> {
        return kotlin.runCatching {
            DayHomeWorkEntity(
                date = model.date.toString(),
                homework = model.homeworks.map { hm ->
                    HomeWorkEntity(
                        id = hm.id,
                        isDone = hm.isDone,
                        lessonName = hm.lessonName,
                        lessonNumber = hm.lessonNumber,
                        startTime = hm.startTime,
                        endTime = hm.endTime,
                        lessonId = hm.lessonId,
                        description = hm.description,
                        isHomeworkElectronicForm = hm.isHomeworkElectronicForm,
                        homeWorkFiles = hm.homeWorkFiles,
                        individualHomeworkDescription = hm.individualHomeworkDescription,
                        isIndividualHomeworkElectronicForm = hm.isIndividualHomeworkElectronicForm,
                        individualHomeWorkFiles = hm.individualHomeWorkFiles
                    )
                }
            )
        }
    }
}