package com.vovka.egov66client.data.dto.grades.subject

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)