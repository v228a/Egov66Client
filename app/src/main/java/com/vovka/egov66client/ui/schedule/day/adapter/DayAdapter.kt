package com.vovka.egov66client.ui.schedule.day.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vovka.egov66client.R
import com.vovka.egov66client.domain.schedule.GetScheduleCurrentWeekUseCase
import com.vovka.egov66client.domain.schedule.GetScheduleWeekUseCase
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.student.LogoutUseCase
import com.vovka.egov66client.ui.schedule.ScheduleFragment
import com.vovka.egov66client.ui.schedule.day.DayFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayAdapter(
    private val fragmentActivity: FragmentActivity,
    private val viewPager: ViewPager2,
    private val getScheduleWeekUseCase: GetScheduleWeekUseCase,
    private val getScheduleCurrentWeekUseCase: GetScheduleCurrentWeekUseCase,
    private val logoutUseCase: LogoutUseCase
) : FragmentStateAdapter(fragmentActivity) {

    private val weekSchedule = MutableStateFlow<WeekScheduleEntity?>(null)
    private val allSchedules = mutableListOf<DayScheduleEntity>()

    init {
        loadSchedule()
    }

    private fun loadSchedule() {
        fragmentActivity.lifecycleScope.launch {
            getScheduleWeekUseCase.invoke()
                .onSuccess { schedule ->
                    weekSchedule.value = schedule
                    allSchedules.addAll(schedule.schedule)
                    notifyDataSetChanged()

                    // Set current item to today's date
                    val currentDate = LocalDate.now()
                    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val currentDayIndex = schedule.schedule.indexOfFirst { day ->
                        val dayDate = LocalDate.parse(day.date, dateFormatter)
                        dayDate == currentDate
                    }

                    if (currentDayIndex != -1) {
                        viewPager.currentItem = currentDayIndex
                    }
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    Log.d("F",fragmentActivity.toString())
                    fragmentActivity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_login,null)
                }
        }
    }

    private fun loadNextSchedule(){
        fragmentActivity.lifecycleScope.launch {
            getScheduleCurrentWeekUseCase.invoke(weekSchedule.value!!.weekNumber+1)
                .onSuccess { schedule ->
                    weekSchedule.value = schedule
                    allSchedules.addAll(schedule.schedule)
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    fragmentActivity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_login,null)
                }
        }
    }

    private fun loadPreviousSchedule() {
        fragmentActivity.lifecycleScope.launch {
            getScheduleCurrentWeekUseCase.invoke(weekSchedule.value!!.weekNumber - 1)
                .onSuccess { schedule ->
                    weekSchedule.value = schedule
                    allSchedules.addAll(0, schedule.schedule)
                    viewPager.currentItem = viewPager.currentItem + 6
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    fragmentActivity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_login,null)
                }
        }
    }
    override fun getItemCount(): Int = allSchedules.size

    override fun createFragment(position: Int): Fragment {
        Log.d("position",position.toString())
        if (position >= itemCount-1) {
            loadNextSchedule()
            Log.d("f","load forward")
        }

        if (position <= 0) {
            Log.d("f","load back")
            loadPreviousSchedule()
        }
        return allSchedules.getOrNull(position)?.let { daySchedule ->
            DayFragment.newInstance(lessons = daySchedule)
        } ?: throw IllegalStateException("Schedule not loaded yet")
    }
}