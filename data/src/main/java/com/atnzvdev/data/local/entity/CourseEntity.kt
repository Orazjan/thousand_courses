package com.atnzvdev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atnzvdev.domain.model.Course


@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rating: Float,
    val startDate: String,
    val isFavorite: Boolean,
    val publishDate: String
)

internal fun Course.toEntity(hasLocalLike: Boolean = this.isFavorite): CourseEntity {
    return CourseEntity(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rating = this.rating,
        startDate = this.startDate,
        isFavorite = hasLocalLike,
        publishDate = this.publishDate
    )
}

internal fun CourseEntity.toDomain(): Course {
    return Course(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rating = this.rating,
        startDate = this.startDate,
        isFavorite = this.isFavorite,
        publishDate = this.publishDate
    )
}