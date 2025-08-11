package com.vovka.egov66client.ui.grades.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemYearGradeBinding
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearPeriodGradeEntity

class YearGradesAdapter(
    private val yearGradesList: List<YearGradeEntity>,
) : RecyclerView.Adapter<YearGradesAdapter.YearGradeViewHolder>() {

    private val gradeColors = mapOf(
        5 to Color.parseColor("#4CAF50"), // Зеленый для отличных оценок
        4 to Color.parseColor("#8BC34A"), // Светло-зеленый для хороших оценок
        3 to Color.parseColor("#FFC107"), // Желтый для удовлетворительных оценок
        2 to Color.parseColor("#FF9800"), // Оранжевый для неудовлетворительных оценок
        1 to Color.parseColor("#F44336")  // Красный для очень плохих оценок
    )

    inner class YearGradeViewHolder(val binding: ItemYearGradeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearGradeViewHolder {
        val binding = ItemYearGradeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YearGradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearGradeViewHolder, position: Int) {
        val currentItem = yearGradesList[position]
        val binding = holder.binding

        // Устанавливаем название предмета
        binding.subjectNameData.text = currentItem.subjectName

        // Настраиваем заголовки периодов в зависимости от количества периодов
        setupPeriodHeaders(binding, currentItem.periodGrades.size)

        // Заполняем данные по периодам
        fillPeriodData(binding, currentItem.periodGrades)

        // Устанавливаем годовую оценку
        binding.yearGradeData.text = currentItem.yearGrade.toString()
        binding.yearGradeData.setBackgroundColor(
            gradeColors[currentItem.yearGrade] ?: Color.parseColor("#6200EE")
        )

        // Устанавливаем экзаменационную оценку
        if (currentItem.examGrade != null) {
            binding.examGradeData.text = currentItem.examGrade.toString()
            binding.examGradeData.setBackgroundColor(
                gradeColors[currentItem.examGrade] ?: Color.parseColor("#6200EE")
            )
            binding.examGradeData.visibility = View.VISIBLE
        } else {
            binding.examGradeData.text = "—"
            binding.examGradeData.visibility = View.VISIBLE
        }

        // Устанавливаем итоговую оценку
        if (currentItem.finalGrade != null) {
            binding.finalGradeData.text = currentItem.finalGrade.toString()
            binding.finalGradeData.setBackgroundColor(
                gradeColors[currentItem.finalGrade] ?: Color.parseColor("#6200EE")
            )
            binding.finalGradeData.visibility = View.VISIBLE
        } else {
            binding.finalGradeData.text = "—"
            binding.finalGradeData.visibility = View.VISIBLE
        }
    }

    private fun setupPeriodHeaders(binding: ItemYearGradeBinding, periodCount: Int) {
        when (periodCount) {
            2 -> {
                // Полугодия
                binding.period1.text = "1 Полуг."
                binding.period2.text = "2 Полуг."
                binding.period3.visibility = View.GONE
                binding.period4.visibility = View.GONE
                binding.period3Container.visibility = View.GONE
                binding.period4Container.visibility = View.GONE
            }
            3 -> {
                // Триместры
                binding.period1.text = "1 Трим."
                binding.period2.text = "2 Трим."
                binding.period3.text = "3 Трим."
                binding.period4.visibility = View.GONE
                binding.period4Container.visibility = View.GONE
            }
            4 -> {
                // Четверти
                binding.period1.text = "1 Четв."
                binding.period2.text = "2 Четв."
                binding.period3.text = "3 Четв."
                binding.period4.text = "4 Четв."
            }
            else -> {
                // По умолчанию показываем все 4 периода
                binding.period1.text = "1 Четв."
                binding.period2.text = "2 Четв."
                binding.period3.text = "3 Четв."
                binding.period4.text = "4 Четв."
            }
        }
    }

    private fun fillPeriodData(binding: ItemYearGradeBinding, periodGrades: List<YearPeriodGradeEntity>) {
        // Очищаем все контейнеры
        clearPeriodContainers(binding)

        // Заполняем данные по периодам
        periodGrades.forEachIndexed { index, periodGrade ->
            when (index) {
                0 -> {
                    binding.period1Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period1Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period1Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                    binding.period1Container.visibility = View.VISIBLE
                }
                1 -> {
                    binding.period2Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period2Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period2Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                    binding.period2Container.visibility = View.VISIBLE
                }
                2 -> {
                    binding.period3Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period3Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period3Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                    binding.period3Container.visibility = View.VISIBLE
                }
                3 -> {
                    binding.period4Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period4Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period4Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                    binding.period4Container.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun clearPeriodContainers(binding: ItemYearGradeBinding) {
        binding.period1Container.visibility = View.GONE
        binding.period2Container.visibility = View.GONE
        binding.period3Container.visibility = View.GONE
        binding.period4Container.visibility = View.GONE
    }

    override fun getItemCount() = yearGradesList.size
}
