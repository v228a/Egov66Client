package com.vovka.egov66client.domain.homework.entity

import com.vovka.egov66client.data.dto.homework.HomeWorkFile

class HomeWorkEntity(
    val id: String,
    val isDone: Boolean,
    val lessonName: String,
    val lessonNumber: Int,
    val startTime: String?,
    val endTime: String?,
    val lessonId: String,
    val description: String?,
    val isHomeworkElectronicForm: Boolean,
    val homeWorkFiles: List<Any>,
    val individualHomeworkDescription: String?,
    val isIndividualHomeworkElectronicForm: Boolean,
    val individualHomeWorkFiles: List<Any>
    //TODO fix any
)