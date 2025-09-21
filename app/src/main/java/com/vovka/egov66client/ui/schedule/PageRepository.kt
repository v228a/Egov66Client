package com.vovka.egov66client.ui.schedule

import com.vovka.egov66client.R


class PageRepository {
    
    private val pages = listOf(
        PageData(
            id = 1,
            title = "Страница 1",
            colorRes = R.color.page_color_1,
            position = 0
        ),
        PageData(
            id = 2,
            title = "Страница 2",
            colorRes = R.color.page_color_2,
            position = 1
        ),
        PageData(
            id = 3,
            title = "Страница 3",
            colorRes = R.color.page_color_3,
            position = 2
        ),
        PageData(
            id = 4,
            title = "Страница сигма найк про",
            colorRes = R.color.page_color_4,
            position = 3
        ),
        PageData(
            id = 5,
            title = "Страница 5",
            colorRes = R.color.page_color_5,
            position = 4
        ),
        PageData(
            id = 6,
            title = "Страница 6",
            colorRes = R.color.page_color_6,
            position = 5
        )
    )
    
    fun getAllPages(): List<PageData> = pages
    
    fun getPageByPosition(position: Int): PageData {
        return pages[position % Constants.MAX_PAGES]
    }
    
    fun getPageById(id: Int): PageData? {
        return pages.find { it.id == id }
    }
    
    fun getTotalPages(): Int = pages.size
}
