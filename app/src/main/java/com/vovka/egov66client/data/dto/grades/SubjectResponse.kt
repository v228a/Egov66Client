package com.vovka.egov66client.data.dto.grades

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.grades.subject.Subject
import com.vovka.egov66client.data.dto.grades.year.Period


data class SubjectResponse(
    @SerializedName("subjects")
    val subjects: List<Subject>
)