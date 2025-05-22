package com.vovka.egov66client.data.dto.student

import com.google.gson.annotations.SerializedName

data class StudentResponse(
    @SerializedName("isParent") val isParent: Boolean,
    @SerializedName("currentUser") val currentUser: User,
    @SerializedName("students") val students: List<Student>
)
//TODO имеется очень интереный баг что просто так взять и получить список учеников нельзя, он защищен протоколом даже с токеном
