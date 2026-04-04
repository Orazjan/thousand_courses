package com.atnzvdev.thousand.presentation.main

data class CourseUiModel(
    val id: Long,
    val title: String,
    val text: String,
    val priceText: String,
    val ratingText: String,
    val startDateText: String,
    val isFavorite: Boolean,
    val publishDateText: String
)
