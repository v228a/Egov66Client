package com.vovka.egov66client.data.dto.grades.period

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Day(
    @SerializedName("date")
    val date: Date
)