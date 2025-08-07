package com.vovka.egov66client.domain.grades.entity.period

import java.time.LocalDateTime

class PeriodLessonGradeEntity(
    val presence: Any?, // может быть null или другим типом, если используется
    val lessonId: String,
    val date: LocalDateTime,
    val grades: List<List<String>> // список списков оценок (возможно, несколько за один урок)
) {
//    // Получить все оценки в виде плоского списка
//    fun getFlatGrades(): List<String> = grades.flatten()
//
//    // Получить числовые оценки (где возможно)
//    fun getNumericGrades(): List<Int> = getFlatGrades()
//        .mapNotNull { it.toIntOrNull() }
}