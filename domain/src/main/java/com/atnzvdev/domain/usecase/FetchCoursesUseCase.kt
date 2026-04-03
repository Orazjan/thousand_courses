package com.atnzvdev.domain.usecase

import com.atnzvdev.domain.repository.CourseRepository
import javax.inject.Inject

class FetchCoursesUseCase @Inject constructor(
    val repository: CourseRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.fetchCoursesFromServer()
    }
}