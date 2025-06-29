package com.vovka.egov66client.data.dto.grades.period

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Grade(
    @SerializedName("presence")
    val presence: String?,

    @SerializedName("lessonId")
    val lessonId: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("grades")
    val grades: List<List<String>>
)