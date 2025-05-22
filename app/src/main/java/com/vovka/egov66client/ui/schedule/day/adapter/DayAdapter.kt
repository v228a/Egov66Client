package com.vovka.egov66client.ui.schedule.day.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.ui.schedule.day.DayFragment

class DayAdapter(
    fragmentActivity: FragmentActivity,
    private val weekSchedule: WeekScheduleEntity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = weekSchedule.schedule.size

    override fun createFragment(position: Int): Fragment {
        Log.d("Positon",position.toString())
        return DayFragment.newInstance(lessons = weekSchedule.schedule[position])
    }
}