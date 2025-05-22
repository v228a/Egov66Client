package com.vovka.egov66client.ui.schedule

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentScheduleBinding
import com.vovka.egov66client.ui.schedule.day.DayViewModel
import com.vovka.egov66client.ui.schedule.day.adapter.DayAdapter
import com.vovka.egov66client.utils.collectWhenStarted
import com.vovka.egov66client.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val viewModel: ScheduleViewModel by viewModels()

    private var _binding: FragmentScheduleBinding? = null
    private val binding: FragmentScheduleBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScheduleBinding.bind(view)
        initCallback()
        subscribe()

    }

    private fun initCallback() {
        viewModel.getWeekSchedule()
    }


    private fun subscribe() {
        viewModel.weekSchedule.observe(viewLifecycleOwner) { schedule ->
            binding.scheduleViewPager.adapter = DayAdapter(requireActivity(), schedule)


            //TODO на будущее переделать
            val currentDate = LocalDate.now()
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val currentDayIndex = schedule.schedule.indexOfFirst { day ->
                val dayDate = LocalDate.parse(day.date, dateFormatter)
                dayDate == currentDate
            }
            
            if (currentDayIndex != -1) {
                binding.scheduleViewPager.setCurrentItem(currentDayIndex, false)
            }
        }

        viewModel.state.collectWhenStarted(this) { state ->
            binding.progressBar.visibleOrGone(state is ScheduleViewModel.State.Loading)
                when(state){
                    is ScheduleViewModel.State.Error -> {}

                    is ScheduleViewModel.State.Loading -> {

                    }
                    is ScheduleViewModel.State.Success -> {

                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}