package com.vovka.egov66client.data.dto.grades.year

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("id")
     val id: String,
    @SerializedName("name")
     val name: String
)