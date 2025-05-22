package com.vovka.egov66client.ui.schedule.day

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentDayBinding
import com.vovka.egov66client.domain.schedule.entity.DayScheduleEntity
import com.vovka.egov66client.domain.schedule.entity.LessonEntity
import com.vovka.egov66client.ui.login.LoginViewModel
import com.vovka.egov66client.ui.schedule.day.adapter.LessonAdapter
import com.vovka.egov66client.utils.collectWhenStarted
import com.vovka.egov66client.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

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
            lessonAdapter = LessonAdapter()
            binding.scheduleRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = lessonAdapter
            }
            lessonAdapter.submitList(daySchedule.lessons)
        }

        binding.btnPrevious.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem - 1
        }

        binding.btnNext.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }

    private fun subscribe(){
        viewModel.state.collectWhenStarted(this) { state ->
//            binding.progressBar.visibleOrGone(state is DayViewModel.State.Loading)
//            when(state){
//                DayViewModel.State.Loading -> {
//                    binding.scheduleRecyclerView.visibility = View.GONE
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//            }
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