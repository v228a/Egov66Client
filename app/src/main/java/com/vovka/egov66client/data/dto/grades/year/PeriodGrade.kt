package com.vovka.egov66client.data.dto.grades.year

import com.google.gson.annotations.SerializedName

data class PeriodGrade(
    @SerializedName("averageGrade") val averageGrade: Double,
    @SerializedName("averageWeightedGrade") val averageWeightedGrade: Double?,
    @SerializedName("finallygrade") val finalGrade: String,
    @SerializedName("periodId") val periodId: String
)