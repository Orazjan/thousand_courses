package com.atnzvdev.domain.usecase

import com.atnzvdev.domain.model.Course
import java.time.LocalDate
import javax.inject.Inject

class SortCoursesByPublishDateUseCase @Inject constructor() {
    operator fun invoke(courses: List<Course>): List<Course> {
        return courses.sortedByDescending { course ->
            parseDate(course.publishDate)
        }


    }

    private fun parseDate(date: String): LocalDate {
        return try {
            LocalDate.parse(date)
        } catch (e: Exception) {
            LocalDate.MIN
        }
    }
}