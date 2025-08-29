package com.vovka.egov66client.ui.grades.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemPeriodGradeBinding
import com.vovka.egov66client.databinding.ItemPeriodLessonGradeBinding
import com.vovka.egov66client.domain.grades.entity.period.PeriodGradeEntity
import com.vovka.egov66client.domain.grades.entity.period.PeriodLessonGradeEntity
import java.time.format.DateTimeFormatter
import java.util.Locale

class PeriodGradesAdapter(
    private val periodGradesList: List<PeriodGradeEntity>,
) : RecyclerView.Adapter<PeriodGradesAdapter.PeriodGradeViewHolder>() {

    init {
        android.util.Log.d("PeriodGradesAdapter", "Создан адаптер с ${periodGradesList.size} элементами")
        if (periodGradesList.isEmpty()) {
            android.util.Log.d("PeriodGradesAdapter", "Список периодических оценок пуст")
        } else {
            periodGradesList.forEachIndexed { index, item ->
                android.util.Log.d("PeriodGradesAdapter", "Элемент $index: ${item.name}, оценок: ${item.grades.size}")
            }
        }
    }

    private val gradeColors = mapOf(
        5 to Color.parseColor("#4CAF50"), // Зеленый для отличных оценок
        4 to Color.parseColor("#8BC34A"), // Светло-зеленый для хороших оценок
        3 to Color.parseColor("#FFC107"), // Желтый для удовлетворительных оценок
        2 to Color.parseColor("#FF9800"), // Оранжевый для неудовлетворительных оценок
        1 to Color.parseColor("#F44336")  // Красный для очень плохих оценок
    )

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())

    inner class PeriodGradeViewHolder(val binding: ItemPeriodGradeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodGradeViewHolder {
        android.util.Log.d("PeriodGradesAdapter", "Создаем ViewHolder")
        val binding = ItemPeriodGradeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeriodGradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeriodGradeViewHolder, position: Int) {
        val currentItem = periodGradesList[position]
        val binding = holder.binding

        android.util.Log.d("PeriodGradesAdapter", "Привязываем элемент $position: ${currentItem.name}")

        // Устанавливаем название предмета
        binding.subjectNameData.text = currentItem.name

        // Устанавливаем среднюю оценку
        if (currentItem.averageGrade != null) {
            binding.averageGradeData.text = String.format("%.2f", currentItem.averageGrade)
            binding.averageGradeData.setBackgroundColor(
                getGradeColor(currentItem.averageGrade)
            )
        } else {
            binding.averageGradeData.text = "—"
            binding.averageGradeData.setBackgroundColor(Color.parseColor("#6200EE"))
        }

        // Устанавливаем средневзвешенную оценку
        if (currentItem.averageWeightedGrade != null) {
            binding.averageWeightedGradeData.text = String.format("%.2f", currentItem.averageWeightedGrade)
            binding.averageWeightedGradeData.setBackgroundColor(
                getGradeColor(currentItem.averageWeightedGrade)
            )
        } else {
            binding.averageWeightedGradeData.text = "—"
            binding.averageWeightedGradeData.setBackgroundColor(Color.parseColor("#6200EE"))
        }

        // Устанавливаем итоговую оценку
        if (currentItem.totalGrade != null) {
            binding.totalGradeData.text = currentItem.totalGrade
            binding.totalGradeData.setBackgroundColor(
                currentItem.totalGrade.toIntOrNull()?.let { gradeColors[it] } 
                    ?: Color.parseColor("#6200EE")
            )
        } else {
            binding.totalGradeData.text = "—"
            binding.totalGradeData.setBackgroundColor(Color.parseColor("#6200EE"))
        }

        // Настраиваем RecyclerView для уроков
        setupLessonsRecyclerView(binding, currentItem.grades)
    }

    private fun setupLessonsRecyclerView(binding: ItemPeriodGradeBinding, lessons: List<PeriodLessonGradeEntity>) {
        binding.lessonsRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.lessonsRecycler.adapter = PeriodLessonsAdapter(lessons)
    }

    private fun getGradeColor(grade: Double): Int {
        return when {
            grade >= 4.5 -> gradeColors[5] ?: Color.parseColor("#6200EE")
            grade >= 3.5 -> gradeColors[4] ?: Color.parseColor("#6200EE")
            grade >= 2.5 -> gradeColors[3] ?: Color.parseColor("#6200EE")
            grade >= 1.5 -> gradeColors[2] ?: Color.parseColor("#6200EE")
            else -> gradeColors[1] ?: Color.parseColor("#6200EE")
        }
    }

    override fun getItemCount(): Int {
        android.util.Log.d("PeriodGradesAdapter", "getItemCount: ${periodGradesList.size}")
        return periodGradesList.size
    }

    // Внутренний адаптер для уроков
    private inner class PeriodLessonsAdapter(
        private val lessons: List<PeriodLessonGradeEntity>
    ) : RecyclerView.Adapter<PeriodLessonsAdapter.LessonViewHolder>() {

        inner class LessonViewHolder(val binding: ItemPeriodLessonGradeBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
            val binding = ItemPeriodLessonGradeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LessonViewHolder(binding)
        }

        override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
            val lesson = lessons[position]
            val binding = holder.binding

            // Устанавливаем дату урока
            binding.lessonDate.text = lesson.date.format(dateFormatter)
            
            // Устанавливаем ID урока
            binding.lessonId.text = "Урок #${lesson.lessonId}"

            // Устанавливаем оценки
            val gradesText = lesson.grades.flatten().joinToString(", ")
            binding.gradesText.text = gradesText

            // Если оценок нет, показываем "Нет оценок"
            if (gradesText.isEmpty()) {
                binding.gradesText.text = "Нет оценок"
                binding.gradesText.setTextColor(Color.parseColor("#757575"))
            } else {
                binding.gradesText.setTextColor(Color.parseColor("#000000"))
            }
        }

        override fun getItemCount() = lessons.size
    }
}
