package com.atnzvdev.domain.usecase

import com.atnzvdev.domain.repository.CourseRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int, isFavorite: Boolean) {
        repository.toggleFavoriteStatus(courseId, isFavorite)
    }
}