package com.vovka.egov66client.ui.schedule

data class CounterState(
    val currentPagePosition: Int = Constants.INITIAL_PAGE_POSITION,
    val numericCounter: Int = Constants.INITIAL_NUMERIC_COUNTER,
    val lastPagePosition: Int = Constants.INITIAL_PAGE_POSITION
)
