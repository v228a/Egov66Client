package com.vovka.egov66client.data.dto.grades.school

import com.google.gson.annotations.SerializedName

data class CurrentYear(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String
)
