package com.atnzvdev.thousand.presentation.main

import com.atnzvdev.domain.model.Course
import java.util.Locale
import javax.inject.Inject

class CourseUiMapper @Inject constructor() {

    fun map(course: Course): CourseUiModel {
        return CourseUiModel(
            id = course.id.toLong(),
            title = course.title,
            text = course.text,
            priceText = formatPrice(course.price),
            ratingText = formatRating(course.rating),
            startDateText = course.startDate,
            isFavorite = course.isFavorite,
            publishDateText = course.publishDate
        )
    }

    fun mapList(courses: List<Course>): List<CourseUiModel> {
        return courses.map(::map)
    }

    private fun formatPrice(price: Int): String {
        return "$price ₽"
    }

    private fun formatRating(rating: Float): String {
        return String.format(Locale.US, "%.1f", rating)
    }
}