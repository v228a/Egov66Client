package com.vovka.egov66client.domain.grades.entity.year

class YearGradeEntity (
    val subjectName: String,           // Название предмета
    val periodGrades: List<YearPeriodGradeEntity>, // Список оценок по периодам
    val yearGrade: Int,                // Годовая оценка
    val examGrade: Int?,               // Оценка за экзамен (может быть null)
    val finalGrade: Int?
)