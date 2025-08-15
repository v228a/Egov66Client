package com.vovka.egov66client.domain.grades.entity.week

class PageWeekDataEntity (
    val pageSize: Int,
    val pageNumber: Int,
    val totalCount: Int,
    val pageActionLink: String?,
    val totalPages: Int,
    val hasPreviousPage: Boolean,
    val hasNextPage: Boolean,
    val pageNumberOutOfRange: Boolean
)