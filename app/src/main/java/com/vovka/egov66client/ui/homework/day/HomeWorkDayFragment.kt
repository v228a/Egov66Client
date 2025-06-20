package com.vovka.egov66client.ui.homework.day


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentHomeWorkDayBinding
import com.vovka.egov66client.databinding.FragmentLoginBinding
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.ui.homework.HomeworkViewModel
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.ui.schedule.day.DayFragment
import com.vovka.egov66client.utils.collectWhenStarted
import com.vovka.egov66client.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import kotlin.getValue

@AndroidEntryPoint
class HomeWorkDayFragment : Fragment(R.layout.fragment_home_work_day) {

    private var date: LocalDateTime? = null

    private var _binding: FragmentHomeWorkDayBinding? = null
    private val binding: FragmentHomeWorkDayBinding get() = _binding!!

    private val viewModel: HomeWorkDayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeWorkDayBinding.bind(view)
        initCallback()
        subscribe()
    }


    private fun subscribe() {
        viewModel.state.collectWhenStarted(this) { state ->
            binding.progressBar.visibleOrGone(state is HomeWorkDayViewModel.State.Loading)
            when(state){
                is HomeWorkDayViewModel.State.Loading -> {

                }
                is HomeWorkDayViewModel.State.Show -> {
                    binding.homeworkRecycler.adapter = HomeWorkDayRecyclerAdapter(state.homeworks.homework)
                    //TODO если нету домашек
                }

                HomeWorkDayViewModel.State.Error -> {

                }
            }
        }

    }

    private fun initCallback(){
        viewModel.updateHomeWork(date!!)
        binding.homeworkRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.dateTextView.text = date?.let { 
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date.from(it.toInstant(ZoneOffset.UTC)))
        }
    }

    companion object {

        fun newInstance(date: LocalDateTime): HomeWorkDayFragment {
            val fragment = HomeWorkDayFragment()
            fragment.date = date
            return fragment
        }
    }
}