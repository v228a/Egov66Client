package com.vovka.egov66client.data.dto.homework

import com.google.gson.annotations.SerializedName

data class Homework(
    @SerializedName("id") val id: String,
    @SerializedName("isDone") val isDone: Boolean,
    @SerializedName("lessonName") val lessonName: String,
    @SerializedName("lessonNumber") val lessonNumber: Int,
    @SerializedName("startTime") val startTime: String?,
    @SerializedName("endTime") val endTime: String?,
    @SerializedName("lessonId") val lessonId: String,
    @SerializedName("description") val description: String?,
    @SerializedName("isHomeworkElectronicForm") val isHomeworkElectronicForm: Boolean,
    @SerializedName("homeWorkFiles") val homeWorkFiles: List<HomeWorkFile>,
    @SerializedName("individualHomeworkDescription") val individualHomeworkDescription: String?,
    @SerializedName("isIndividualHomeworkElectronicForm") val isIndividualHomeworkElectronicForm: Boolean,
    @SerializedName("individualHomeWorkFiles") val individualHomeWorkFiles: List<HomeWorkFile>
)