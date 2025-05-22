package com.vovka.egov66client.data.dto.student

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("surName") val surName: String,
    @SerializedName("className") val className: String,
    @SerializedName("orgName") val orgName: String,
    @SerializedName("id") val id: String,
    @SerializedName("avatarId") val avatarId: String?
)