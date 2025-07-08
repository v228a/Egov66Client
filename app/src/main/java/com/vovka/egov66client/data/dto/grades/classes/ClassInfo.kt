package com.vovka.egov66client.data.dto.grades.classes

import com.google.gson.annotations.SerializedName

data class ClassInfo(
    @SerializedName("value")
    val id: String,

    @SerializedName("text")
    val name: String
)