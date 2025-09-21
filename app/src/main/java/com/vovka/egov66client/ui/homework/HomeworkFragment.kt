package com.vovka.egov66client.ui.homework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentHomeworkBinding
import com.vovka.egov66client.ui.homework.day.HomeWorkDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class HomeworkFragment : Fragment(R.layout.fragment_homework) {

    private val viewModel: HomeworkViewModel by viewModels()

    private var _binding: FragmentHomeworkBinding? = null
    private val binding: FragmentHomeworkBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeworkBinding.bind(view)
        initCallback()
        subscribe()

    }

    private fun subscribe() {
        val adapter = HomeWorkDayAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(adapter.getStartPosition(), false)
    }

    private fun initCallback(){}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}