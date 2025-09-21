package com.vovka.egov66client.ui.schedule


class UpdateCounterUseCase {
    
    fun updateCounters(currentState: CounterState, newPageData: PageData): CounterState {
        var newNumericCounter = currentState.numericCounter
        
        // Проверяем, завершили ли мы полный цикл (прошли все 6 страниц)
        // Переход с последней страницы (5) на первую (0) означает завершение цикла
        if (newPageData.position == 0 && currentState.currentPagePosition == Constants.MAX_PAGES - 1) {
            // Переход с последней страницы на первую - увеличиваем числовой счетчик
            if (currentState.numericCounter < Constants.MAX_NUMERIC_COUNTER) {
                newNumericCounter = currentState.numericCounter + 1
            }
        }
        
        return CounterState(
            currentPagePosition = newPageData.position,
            numericCounter = newNumericCounter,
            lastPagePosition = currentState.currentPagePosition
        )
    }
    
    fun getFractionalCounterText(currentPagePosition: Int): String {
        return "${currentPagePosition + 1}/${Constants.MAX_PAGES}"
    }
    
    fun getNumericCounterText(numericCounter: Int): String {
        return numericCounter.toString()
    }
}
