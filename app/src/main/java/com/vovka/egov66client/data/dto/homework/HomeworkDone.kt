package com.vovka.egov66client.data.dto.homework


import com.google.gson.annotations.SerializedName
class HomeworkDone(
    @SerializedName("homeworkId")
    val homeworkId: String,

    @SerializedName("isDone")
    val isDone: Boolean,

    @SerializedName("studentId")
    val studentId: String
)