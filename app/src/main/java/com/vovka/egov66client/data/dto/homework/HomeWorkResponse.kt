package com.vovka.egov66client.data.dto.homework

import com.google.gson.annotations.SerializedName

data class HomeWorkResponse(
    @SerializedName("date") val date: String,
    @SerializedName("pagination") val pagination: PaginationHomeWork,
    @SerializedName("homeworks") val homeworks: List<Homework>
)