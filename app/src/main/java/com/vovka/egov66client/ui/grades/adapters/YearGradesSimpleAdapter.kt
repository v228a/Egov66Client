package com.vovka.egov66client.ui.grades.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemYearGradeSimpleBinding
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearPeriodGradeEntity

class YearGradesSimpleAdapter(
    private val yearGradesList: List<YearGradeEntity>,
) : RecyclerView.Adapter<YearGradesSimpleAdapter.YearGradeViewHolder>() {

    private val gradeColors = mapOf(
        5 to Color.parseColor("#4CAF50"), // Зеленый для отличных оценок
        4 to Color.parseColor("#8BC34A"), // Светло-зеленый для хороших оценок
        3 to Color.parseColor("#FFC107"), // Желтый для удовлетворительных оценок
        2 to Color.parseColor("#FF9800"), // Оранжевый для неудовлетворительных оценок
        1 to Color.parseColor("#F44336")  // Красный для очень плохих оценок
    )

    inner class YearGradeViewHolder(val binding: ItemYearGradeSimpleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearGradeViewHolder {
        val binding = ItemYearGradeSimpleBinding.inflate(
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
        binding.subjectName.text = currentItem.subjectName

        // Настраиваем данные по периодам
        setupPeriodData(binding, currentItem.periodGrades)

        // Устанавливаем годовую оценку
        binding.yearGrade.text = currentItem.yearGrade.toString()
        binding.yearGrade.setBackgroundColor(
            gradeColors[currentItem.yearGrade] ?: Color.parseColor("#6200EE")
        )

        // Устанавливаем экзаменационную оценку
        if (currentItem.examGrade != null) {
            binding.examGrade.text = currentItem.examGrade.toString()
            binding.examGrade.setBackgroundColor(
                gradeColors[currentItem.examGrade] ?: Color.parseColor("#6200EE")
            )
        } else {
            binding.examGrade.text = "—"
        }

        // Устанавливаем итоговую оценку
        if (currentItem.finalGrade != null) {
            binding.finalGrade.text = currentItem.finalGrade.toString()
            binding.finalGrade.setBackgroundColor(
                gradeColors[currentItem.finalGrade] ?: Color.parseColor("#6200EE")
            )
        } else {
            binding.finalGrade.text = "—"
        }
    }

    private fun setupPeriodData(binding: ItemYearGradeSimpleBinding, periodGrades: List<YearPeriodGradeEntity>) {
        // Очищаем все контейнеры
        clearPeriodContainers(binding)

        // Определяем тип периодов
        val periodType = when (periodGrades.size) {
            2 -> "Полугодие"
            3 -> "Триместр"
            4 -> "Четверть"
            else -> "Четверть"
        }

        // Заполняем данные по периодам
        periodGrades.forEachIndexed { index, periodGrade ->
            when (index) {
                0 -> {
                    binding.period1Container.visibility = View.VISIBLE
                    binding.period1Title.text = "1 $periodType"
                    binding.period1Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period1Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period1Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                }
                1 -> {
                    binding.period2Container.visibility = View.VISIBLE
                    binding.period2Title.text = "2 $periodType"
                    binding.period2Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period2Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period2Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                }
                2 -> {
                    binding.period3Container.visibility = View.VISIBLE
                    binding.period3Title.text = "3 $periodType"
                    binding.period3Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period3Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period3Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                }
                3 -> {
                    binding.period4Container.visibility = View.VISIBLE
                    binding.period4Title.text = "4 $periodType"
                    binding.period4Avg.text = String.format("%.2f", periodGrade.grade)
                    binding.period4Final.text = periodGrade.rank?.toString() ?: "—"
                    binding.period4Final.setBackgroundColor(
                        periodGrade.rank?.let { gradeColors[it] } ?: Color.parseColor("#6200EE")
                    )
                }
            }
        }
    }

    private fun clearPeriodContainers(binding: ItemYearGradeSimpleBinding) {
        binding.period1Container.visibility = View.GONE
        binding.period2Container.visibility = View.GONE
        binding.period3Container.visibility = View.GONE
        binding.period4Container.visibility = View.GONE
    }

    override fun getItemCount() = yearGradesList.size
}
