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
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.ui.grades.adapters.GradesWeekAdapter
import com.vovka.egov66client.ui.grades.adapters.PeriodGradesAdapter
import com.vovka.egov66client.ui.grades.adapters.YearGradesAdapter
import com.vovka.egov66client.ui.grades.adapters.YearGradesCompactAdapter
import com.vovka.egov66client.ui.grades.adapters.YearGradesSimpleAdapter
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

    private fun loadGradesIfAllFieldsFilled() {
        val yearText = binding.yearDropDown.text.toString()
        val periodText = binding.periodDropDown.text.toString()
        val subjectText = binding.subjectDropDown.text.toString()
        
        Log.d("GradesFragment", "Проверяем поля: yearText='$yearText', periodText='$periodText', subjectText='$subjectText'")
        
        if (yearText.isNotEmpty() && periodText.isNotEmpty() && subjectText.isNotEmpty()) {
            val subjectId = viewModel.getSubjectIdByName(subjectText)
            val periodId = viewModel.getPeriodIdByName(periodText)
            val yearId = viewModel.getYearIdByName(yearText)
            
            Log.d("GradesFragment", "ID: subjectId='$subjectId', periodId='$periodId', yearId='$yearId'")
            
            viewModel.loadGrades(
                subjectId = subjectId,
                periodId = periodId,
                yearId = yearId,
                weekNumber = null
            )
        } else {
            Log.d("GradesFragment", "Не все поля заполнены")
        }
        Log.d("GradesFragment","Я работаю")
    }

    private fun subscribe() {
        // TextWatcher для года
        binding.yearDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("yearDropDown", "Текст изменён: $s")
                viewModel.updatePeriodAndSubject(s.toString())
                loadGradesIfAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // TextWatcher для периода
        binding.periodDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("periodDropDown", "Текст изменён: $s")
                loadGradesIfAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // TextWatcher для предмета
        binding.subjectDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("subjectDropDown", "Текст изменён: $s")
                loadGradesIfAllFieldsFilled()
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

                is GradesViewModel.Action.LoadPeriodGrades -> {
                    Log.d("LoadPeriodGrades", "Загружены периодические оценки: ${action.periodData.size}")
                    Log.d("LoadPeriodGrades", "Данные: ${action.periodData}")
                    if (action.periodData.isEmpty()) {
                        Log.d("LoadPeriodGrades", "Нет данных для отображения")
                        // Можно добавить отображение сообщения "Нет данных"
                    }
                    val adapter = PeriodGradesAdapter(action.periodData)
                    binding.gradesRecycler.adapter = adapter
                    Log.d("LoadPeriodGrades", "Адаптер установлен")
                }
                is GradesViewModel.Action.LoadWeekGrades -> {
                    Log.d("LoadWeekGrades", "Загружены недельные оценки: ${action.weekData.size}")
                    val adapter = GradesWeekAdapter(action.weekData)
                    binding.gradesRecycler.adapter = adapter
                }
                is GradesViewModel.Action.LoadYearGrades -> {
                    Log.d("LoadYearGrades", "Загружены годовые оценки: ${action.yearData.size}")
                    val adapter = YearGradesSimpleAdapter(action.yearData)
                    binding.gradesRecycler.adapter = adapter
                }
            }
        }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}