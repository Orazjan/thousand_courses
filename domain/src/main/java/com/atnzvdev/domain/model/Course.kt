package com.atnzvdev.domain.model

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rating: Float,
    val startDate: String,
    val isFavorite: Boolean,
    val publishDate: String
)
