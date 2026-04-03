package com.atnzvdev.domain.usecase

import com.atnzvdev.domain.model.Course
import com.atnzvdev.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getCoursesStream()
    }
}