package com.vovka.egov66client.data.dto.grades.period

import com.google.gson.annotations.SerializedName


data class Discipline(
    @SerializedName("name")
    val name: String,

    @SerializedName("averageGrade")
    val averageGrade: Double,

    @SerializedName("averageWeightedGrade")
    val averageWeightedGrade: Double?,

    @SerializedName("totalGrade")
    val totalGrade: String?,

    @SerializedName("grades")
    val grades: List<Grade>
)