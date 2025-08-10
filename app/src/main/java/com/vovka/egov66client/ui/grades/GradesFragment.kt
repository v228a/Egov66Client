package com.vovka.egov66client.ui.grades

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    //Первое фаза - загрузка настроек
    //Вторая фаза - загрузка настроек с настроект
    //Год - фиксированный, от него меняется список предметов
    //Когда менется год, нужно менять
    //3 возможных recycler - недельный, периодичный, итоговый
    //Периодичный - полугодие, четверть


    private fun subscribe() {
        binding.yearDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("yearDropDown", "Текст изменён: $s")
                viewModel.updatePeriodAndSubject(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.action.collectWhenStarted(this) { action ->
            when(action){
                is GradesViewModel.Action.UpdatePeriod -> {
                    val periodData = action.periodData
                    if (!periodData.isNullOrEmpty()){
                        binding.periodDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            R.layout.simple_dropdown_item_1line,
                            (periodData.map { it.name })
                        ))
                        binding.periodDropDown.setText(periodData.map { it.name }.get(0),false)
                    }else{
                        Log.d("UpdatePeriod","PeriodData in null")
                    }
                }
                is GradesViewModel.Action.UpdateSubject -> {
                    Log.d("UpdateSubject","I am working")
                    Log.d("UpdateSubject",action.subjectData!!.isEmpty().toString())
                    val subjectData = action.subjectData
                    if (!subjectData.isNullOrEmpty()){
                        binding.subjectDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            R.layout.simple_dropdown_item_1line,
                            (subjectData.map { it.name })
                        ))
                        binding.subjectDropDown.setText(subjectData.map { it.name }.get(0),false)
                    }
                }
                
                is GradesViewModel.Action.UpdateYear -> {
                    Log.d("UpdateYear","I am working")
                    val yearData = action.yearData
                    if (!yearData.isNullOrEmpty()){
                        binding.yearDropDown.setAdapter(ArrayAdapter(
                            requireContext(),
                            R.layout.simple_dropdown_item_1line,
                            (yearData.map { it.name })
                        ))
                        val index = yearData.indexOf(yearData.find { it.id == viewModel.getCurrentYear().id})
                        binding.yearDropDown.setText(yearData.map { it.name }.get(index),false)
                    }
                }

                is GradesViewModel.Action.LoadPeriodGrades -> TODO()
                is GradesViewModel.Action.LoadWeekGrades -> TODO()
                is GradesViewModel.Action.LoadYearGrades -> TODO()
            }
        }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}