package com.vovka.egov66client.data.dto.grades

import com.google.gson.annotations.SerializedName
import com.vovka.egov66client.data.dto.grades.classes.ClassInfo

data class ClassesResponse(
    @SerializedName("currentClass")
    val currentClass: ClassInfo,

    @SerializedName("gradeItemModels")
    val availableClasses: List<ClassInfo>
)