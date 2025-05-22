package com.vovka.egov66client.data.dto.student

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("surName") val surName: String
)