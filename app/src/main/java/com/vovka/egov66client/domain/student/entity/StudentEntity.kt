package com.vovka.egov66client.domain.student.entity

import com.google.gson.annotations.SerializedName

class StudentEntity(
     val firstName: String,
    val lastName: String,
    val surName: String,
 val className: String,
 val orgName: String,
 val id: String,
 val avatarId: String?
)