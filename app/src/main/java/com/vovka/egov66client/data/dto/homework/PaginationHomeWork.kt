package com.vovka.egov66client.data.dto.homework

import com.google.gson.annotations.SerializedName

data class PaginationHomeWork(
    @SerializedName("nextDate") val nextDate: String?,
    @SerializedName("previousDate") val previousDate: String?
)