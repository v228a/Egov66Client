package com.vovka.egov66client.data.dto.homework

import com.google.gson.annotations.SerializedName

data class HomeWorkFile(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
)