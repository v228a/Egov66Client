package com.vovka.egov66client.ui.grades

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vovka.egov66client.databinding.FragmentGradesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GradesFragment : Fragment(com.vovka.egov66client.R.layout.fragment_grades) {

    private val viewModel: GradesViewModel by viewModels()
    private var _binding: FragmentGradesBinding? = null
    private val binding: FragmentGradesBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGradesBinding.bind(view)
        initCallback()
        subscribe()
    }



    private fun initCallback() {
        val suggestions = arrayOf("Apple", "Banana", "Cherry", "Date", "Fig", "Grape")
        val adapter = ArrayAdapter(
            requireContext(), // or this@YourActivity if in Activity
            android.R.layout.simple_dropdown_item_1line,
            suggestions
        )

        // Set the adapter
        binding.yearDropDown.setAdapter(adapter)
        binding.periodDropDown.setAdapter(adapter)
        binding.subjectDropDown.setAdapter(adapter)
        binding.yearDropDown.setText(suggestions.get(0),false)


    }



    private fun subscribe() {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}