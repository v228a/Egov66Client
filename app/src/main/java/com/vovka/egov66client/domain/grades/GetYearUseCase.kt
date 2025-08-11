package com.vovka.egov66client.domain.grades

import com.vovka.egov66client.domain.grades.entity.PeriodEntity
import com.vovka.egov66client.domain.grades.entity.year.YearGradeEntity
import com.vovka.egov66client.domain.grades.entity.year.YearPeriodGradeEntity
import com.vovka.egov66client.domain.grades.repo.GradesRepository
import javax.inject.Inject

class GetYearUseCase @Inject constructor(

) {
    suspend operator fun invoke(schoolYear: String = ""): Result<List<YearGradeEntity>> {
        return runCatching {
            listOf(
                // Пример с четвертями (как в вашем исходном скриншоте)
                YearGradeEntity(
                    subjectName = "Информатика",
                    periodGrades = listOf(
                        YearPeriodGradeEntity("1 четверть", 4.57, 5),
                        YearPeriodGradeEntity("2 четверть", 3.91, 4),
                        YearPeriodGradeEntity("3 четверть", 3.88, 4),
                        YearPeriodGradeEntity("4 четверть", 3.83, 4)
                    ),
                    yearGrade = 4,
                    examGrade = 4,
                    finalGrade = 4
                ),

                // Пример с полугодиями
                YearGradeEntity(
                    subjectName = "Математика",
                    periodGrades = listOf(
                        YearPeriodGradeEntity("1 полугодие", 4.25, 2),
                        YearPeriodGradeEntity("2 полугодие", 4.75, 1)
                    ),
                    yearGrade = 5,
                    examGrade = 5,
                    finalGrade = 5
                ),

                // Пример с триместрами (без ранга)
                YearGradeEntity(
                    subjectName = "Физика",
                    periodGrades = listOf(
                        YearPeriodGradeEntity("1 триместр", 3.67, null),
                        YearPeriodGradeEntity("2 триместр", 4.33, null),
                        YearPeriodGradeEntity("3 триместр", 4.00, null)
                    ),
                    yearGrade = 4,
                    examGrade = null,  // Нет экзамена
                    finalGrade = 4
                ),

                // Пример с одним предметом, где есть только годовая оценка
                YearGradeEntity(
                    subjectName = "Физкультура",
                    periodGrades = emptyList(),  // Нет промежуточных оценок
                    yearGrade = 5,
                    examGrade = null,
                    finalGrade = 5
                ),

                // Пример с низкими оценками
                YearGradeEntity(
                    subjectName = "Химия",
                    periodGrades = listOf(
                        YearPeriodGradeEntity("1 четверть", 3.25, 10),
                        YearPeriodGradeEntity("2 четверть", 3.50, 8),
                        YearPeriodGradeEntity("3 четверть", 2.75, 12),
                        YearPeriodGradeEntity("4 четверть", 3.00, 9)
                    ),
                    yearGrade = 3,
                    examGrade = 3,
                    finalGrade = 3
                )
            )
        }
    }
}