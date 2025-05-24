package com.vovka.egov66client.ui.schedule.day

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentDayBinding
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.ui.schedule.day.adapter.LessonAdapter
import com.vovka.egov66client.utils.collectWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayFragment : Fragment(R.layout.fragment_day) {

    private var lessons: DayScheduleEntity? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var lessonAdapter: LessonAdapter

    private var _binding: FragmentDayBinding? = null
    private val binding: FragmentDayBinding get() = _binding!!

    private val viewModel: DayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDayBinding.bind(view)
        viewPager = requireActivity().findViewById(R.id.scheduleViewPager)
        initCallback()
        subscribe()
    }



    private fun initCallback(){
        lessons?.let { daySchedule ->
            binding.dateTextView.text = daySchedule.date
            //viewmodel не работает(
            if (daySchedule.isWeekend){
//                viewModel.showTextHoliday()
                binding.exceptionTextView.text = "Выходной день"
            }
            else if (daySchedule.isCelebration){
//                viewModel.showTextCelebration()
                binding.exceptionTextView.text = "С праздником"
            }
            else if(daySchedule.lessons.isEmpty()){
//                viewModel.showTextNoLessons()
                binding.exceptionTextView.text = "Сегодня уроков нет"
            }
            else{
                lessonAdapter = LessonAdapter()
                binding.scheduleRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = lessonAdapter
                }
                lessonAdapter.submitList(daySchedule.lessons)
            }

        }

        binding.btnPrevious.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem - 1
        }

        binding.btnNext.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem + 1
        }

    }

    private fun subscribe(){
        viewModel.action.collectWhenStarted(this) { action ->
            when(action) {
                DayViewModel.Action.ShowTextCelebration -> TODO()
                DayViewModel.Action.ShowTextHoliday -> TODO()
                DayViewModel.Action.ShowTextNoLessons -> {
//                    Log.d("f","adsfadf")
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(lessons: DayScheduleEntity): DayFragment {
            val fragment = DayFragment()
            fragment.lessons = lessons
            return fragment
        }
    }
}