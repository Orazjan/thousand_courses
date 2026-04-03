package com.atnzvdev.domain.repository

import com.atnzvdev.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCoursesStream(): Flow<List<Course>>
    fun getFavoriteCourses(): Flow<List<Course>>
    suspend fun fetchCoursesFromServer(): Result<Unit>
    suspend fun toggleFavoriteStatus(courseId: Int, isFavorite: Boolean)
}