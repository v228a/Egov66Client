package com.vovka.egov66client.ui.grades

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovka.egov66client.databinding.FragmentGradesBinding
import com.vovka.egov66client.ui.grades.adapters.GradesWeekAdapter
import com.vovka.egov66client.ui.grades.adapters.PeriodGradesAdapter
import com.vovka.egov66client.ui.grades.adapters.YearGradesSimpleAdapter
import com.vovka.egov66client.utils.collectWhenStarted
import com.vovka.egov66client.utils.visibleOrGone
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

    private fun loadGradesIfAllFieldsFilled() {
        val yearText = binding.yearDropDown.text.toString()
        val periodText = binding.periodDropDown.text.toString()
        val subjectText = binding.subjectDropDown.text.toString()
        
        if (yearText.isNotEmpty() && periodText.isNotEmpty() && subjectText.isNotEmpty()) {
            val subjectId = viewModel.getSubjectIdByName(subjectText)
            val periodId = viewModel.getPeriodIdByName(periodText)
            val yearId = viewModel.getYearIdByName(yearText)
            
            viewModel.loadGrades(
                subjectId = subjectId,
                periodId = periodId,
                yearId = yearId,
                weekNumber = null
            )
        }
    }

    private fun subscribe() {
        // TextWatcher для года
        binding.yearDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updatePeriodAndSubject(s.toString())
                loadGradesIfAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // TextWatcher для периода
        binding.periodDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadGradesIfAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // TextWatcher для предмета
        binding.subjectDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadGradesIfAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Подписка на action для обновления настроек
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
                    }
                }
                is GradesViewModel.Action.UpdateSubject -> {
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
            }
        }

        // Подписка на state для загрузки оценок
        viewModel.state.collectWhenStarted(this) { state ->
            when (state) {
                is GradesViewModel.State.LoadingSettings -> {
                    // Показываем индикатор загрузки настроек если нужно
                }
                is GradesViewModel.State.LoadingGrades -> {
                    // Показываем индикатор загрузки оценок
                    // Можно добавить ProgressBar или другой индикатор
                }
                is GradesViewModel.State.LoadPeriodGrades -> {
                    binding.numberWeekSelector.visibleOrGone(false)
                    if (state.periodData.isEmpty()) {
                        // TODO добавить отображение сообщения "Нет данных"
                    }
                    val adapter = PeriodGradesAdapter(state.periodData)
                    binding.gradesRecycler.adapter = adapter
                }
                is GradesViewModel.State.LoadWeekGrades -> {
                    binding.numberWeekSelector.visibleOrGone(true)
                    val adapter = GradesWeekAdapter(state.weekData)
                    if (state.weekData.isEmpty()) {
                        // TODO добавить отображение сообщения "Нет данных"
                    }
                    binding.gradesRecycler.adapter = adapter
                }
                is GradesViewModel.State.LoadYearGrades -> {
                    binding.numberWeekSelector.visibleOrGone(true)
                    val adapter = YearGradesSimpleAdapter(state.yearData)
                    if (state.yearData.isEmpty()) {
                        // TODO добавить отображение сообщения "Нет данных"
                    }
                    binding.gradesRecycler.adapter = adapter
                }
                is GradesViewModel.State.Error -> {
                    // Обработка ошибки
                    // TODO добавить отображение ошибки пользователю
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}