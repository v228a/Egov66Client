package com.vovka.egov66client.ui.homework.day

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.Calendar
import java.util.Date

class HomeWorkDayAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val calendar = Calendar.getInstance()

    // Начальная позиция для бесконечной прокрутки
    private val startPosition = Int.MAX_VALUE / 2

    override fun createFragment(position: Int): Fragment {
        // Вычисляем реальную дату на основе позиции
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_MONTH, position - startPosition)
        return HomeWorkDayFragment.newInstance(calendar.time)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    fun getStartPosition(): Int = startPosition
}