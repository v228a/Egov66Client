package com.vovka.egov66client.ui.schedule.day.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vovka.egov66client.R
import com.vovka.egov66client.domain.schedule.GetScheduleCurrentWeekUseCase
import com.vovka.egov66client.domain.schedule.GetScheduleWeekUseCase
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.domain.schedule.entity.WeekScheduleEntity
import com.vovka.egov66client.domain.student.LogoutUseCase
import com.vovka.egov66client.ui.schedule.ScheduleFragment
import com.vovka.egov66client.ui.schedule.day.DayFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.compareTo
import kotlin.math.log
import kotlin.text.compareTo

class DayAdapter(
    private val fragmentActivity: FragmentActivity,
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
                        (fragmentActivity as? ScheduleFragment)?.setCurrentItem(currentDayIndex)
                    }
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    (fragmentActivity as? DayFragment)?.findNavController()?.navigate(R.id.navigation_login,null)
                }
        }
    }
    
    private fun loadNextSchedule(){
        fragmentActivity.lifecycleScope.launch {
            getScheduleCurrentWeekUseCase.invoke(weekSchedule.value!!.weekNumber+1)
                .onSuccess { schedule ->
                    weekSchedule.value = schedule
                    allSchedules.addAll(schedule.schedule)
                    notifyDataSetChanged()
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    (fragmentActivity as? ScheduleFragment)?.findNavController()?.navigate(R.id.navigation_login,null)
                }
        }
    }

    private fun loadPreviousSchedule() {
        fragmentActivity.lifecycleScope.launch {
            getScheduleCurrentWeekUseCase.invoke(weekSchedule.value!!.weekNumber - 1)
                .onSuccess { schedule ->
                    Log.d("f",allSchedules.toString())
                    weekSchedule.value = schedule
                    allSchedules.addAll(0, schedule.schedule)
                    Log.d("f",allSchedules.toString())
                    notifyDataSetChanged()
                }
                .onFailure { error ->
                    logoutUseCase.invoke()
                    (fragmentActivity as? ScheduleFragment)?.findNavController()?.navigate(R.id.navigation_login,null)
                }
        }
    }


    override fun getItemCount(): Int = allSchedules.size

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount-1) {
            loadNextSchedule()
        }

        if (position <= 3) {
//            loadPreviousSchedule()
            Log.d("f","back")
        }
        return allSchedules.getOrNull(position)?.let { daySchedule ->
            DayFragment.newInstance(lessons = daySchedule)
        } ?: throw IllegalStateException("Schedule not loaded yet")
    }
}