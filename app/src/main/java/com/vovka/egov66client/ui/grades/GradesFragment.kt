package com.vovka.egov66client.ui.grades

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentGradesBinding
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.ui.grades.adapters.GradesWeekAdapter
import com.vovka.egov66client.ui.login.LoginViewModel
import com.vovka.egov66client.utils.collectWhenStarted
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

        viewModel.loadSettings()
    }



    private fun initCallback() {
        val gradesList = listOf(
        GradeWeekEntity("1", "5", "Математика", "2", "8:40 - 9:20"),
        GradeWeekEntity("2", "4", "Физика", "3", "9:30 - 10:10"),
        GradeWeekEntity("3", "5", "Литература", "4", "10:20 - 11:00")
    )

        val adapter = GradesWeekAdapter(gradesList)
        binding.gradesRecycler.adapter = adapter
        binding.gradesRecycler.layoutManager = LinearLayoutManager(requireContext())


    }



    private fun subscribe() {
        viewModel.action.collectWhenStarted(this) { action ->
            when(action) {
                GradesViewModel.Action.ChangePeriod -> TODO()
                GradesViewModel.Action.ChangeSubject -> TODO()
                GradesViewModel.Action.ChangeYear -> TODO()
                is GradesViewModel.Action.ShowSettings -> {
                    val yearData = action.yearData.map { it.name }
                    val periodData = action.periodData.map { it.name }
                    val subjectsData = action.subjectData.map { it.name }

                    binding.yearDropDown.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        (yearData)
                    ))
                    binding.yearDropDown.setText(yearData.get(0),false)

                    binding.subjectDropDown.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        (subjectsData)
                    ))
                    binding.subjectDropDown.setText(subjectsData.get(0),false)

                    binding.periodDropDown.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        (periodData)
                    ))
                    binding.periodDropDown.setText(periodData.get(0),false)


                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}