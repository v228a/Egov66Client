package com.vovka.egov66client.ui.grades.adapters


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemGradeWeekBinding
import com.vovka.egov66client.domain.grades.entity.week.WeekGradesListEntity

class GradesWeekAdapter(
    private val gradesList: List<WeekGradesListEntity>,
) : RecyclerView.Adapter<GradesWeekAdapter.GradeViewHolder>() {

    private val gradeColors = mapOf(
        "5" to Color.parseColor("#4CAF50"),
        "4" to Color.parseColor("#8BC34A"),
        "3" to Color.parseColor("#FFC107"),
        "2" to Color.parseColor("#FF9800"),
        "1" to Color.parseColor("#F44336")
    )

    inner class GradeViewHolder(val binding: ItemGradeWeekBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val binding = ItemGradeWeekBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val currentItem = gradesList[position]

        with(holder.binding) {
            // Устанавливаем оценку и цвет
            gradeCircle.text = currentItem.grade
            gradeCircle.setBackgroundColor(
                gradeColors[currentItem.grade] ?: Color.parseColor("#6200EE")
            )

            // Устанавливаем данные урока
            subjectName.text = currentItem.lesson
            lessonNumber.text = currentItem.lessonNumber
            lessonTime.text = currentItem.time

            // Пример для даты и дня недели (если они есть в модели)
//             date.text = currentItem.time
//             dayOfWeek.text = currentItem.dayOfWeek

            // Выделение текущего дня
        }
    }

    override fun getItemCount() = gradesList.size
}