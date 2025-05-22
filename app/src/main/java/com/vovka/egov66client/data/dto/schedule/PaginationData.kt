package com.vovka.egov66client.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class PaginationData(
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("pageActionLink") val pageActionLink: String?,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("hasPreviousPage") val hasPreviousPage: Boolean,
    @SerializedName("hasNextPage") val hasNextPage: Boolean,
    @SerializedName("pageNumberOutOfRange") val pageNumberOutOfRange: Boolean
)