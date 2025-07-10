package com.vovka.egov66client.ui.grades

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovka.egov66client.databinding.FragmentGradesBinding
import com.vovka.egov66client.domain.grades.entity.GradeWeekEntity
import com.vovka.egov66client.ui.grades.adapters.GradesWeekAdapter
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

        viewModel.loadAllSettings()
    }



    private fun initCallback() {
        binding.gradesRecycler.layoutManager = LinearLayoutManager(requireContext())
    }



    private fun subscribe() {
        viewModel.action.collectWhenStarted(this) { action ->
            when(action) {
                GradesViewModel.Action.ChangePeriod -> TODO()
                GradesViewModel.Action.ChangeSubject -> TODO()
                GradesViewModel.Action.ChangeYear -> TODO()
                is GradesViewModel.Action.ShowYears -> {
                    val yearData = action.yearData
                    if (!yearData.isNullOrEmpty()){
                        binding.yearDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            (yearData.map { it.name })
                        ))
                        binding.yearDropDown.setText(yearData.map { it.name }.get(0),false)
                        binding.yearDropDown.setOnItemClickListener { parent, view, position, id ->
                            // Тригерим обновление предметов для выбранного года
                            viewModel.loadSubjects(yearData[position].name.replace("/","-"))
//                            // Сбросить и очистить выпадающий список предметов
                            binding.subjectDropDown.setAdapter(ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                listOf<String>()
                            ))
                            binding.subjectDropDown.setText("", false)
                        }
                    }
                }
                is GradesViewModel.Action.ShowSubjects -> {
                    val subjectsData = action.subjectData
                    if (!subjectsData.isNullOrEmpty()){
                        binding.subjectDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            (subjectsData.map { it.name })
                        ))
                        binding.subjectDropDown.setText(subjectsData.map { it.name }.get(0),false)
                    }
                }
                is GradesViewModel.Action.ShowPeriods -> {
                    val periodData = action.periodData
                    if (!periodData.isNullOrEmpty()){
                        binding.periodDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            (periodData.map { it.name })
                        ))
                        binding.periodDropDown.setText(periodData.map { it.name }.get(0),false)
                    }
                }
                is GradesViewModel.Action.ShowSettings -> {
                    // All settings loaded, now trigger grades loading
                    val yearData = action.yearData
                    val periodData = action.periodData
                    val subjectsData = action.subjectData
                    // Optionally, set dropdowns again if needed
                    viewModel.loadWeekGrades(
                        // pass appropriate ids if needed
                    )
                }
                is GradesViewModel.Action.ShowWeekGrades -> {
                    binding.gradesRecycler.adapter = GradesWeekAdapter(action.grades)
                }
            }
        }












    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}