package com.vovka.egov66client.ui.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentScheduleBinding
import com.vovka.egov66client.ui.schedule.day.adapter.DayAdapter
import com.vovka.egov66client.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint

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
        binding.scheduleViewPager.adapter = DayAdapter(requireActivity(), viewModel.getScheduleWeekUseCase,viewModel.getScheduleCurrentWeekUseCase)
    }

    fun setCurrentItem(position: Int) {
        binding.scheduleViewPager.setCurrentItem(position, false)
    }

    private fun subscribe() {
//        viewModel.state.collectWhenStarted(this) { state ->
//            binding.progressBar.visibleOrGone(state is ScheduleViewModel.State.Loading)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}