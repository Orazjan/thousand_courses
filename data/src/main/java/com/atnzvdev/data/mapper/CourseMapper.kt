package com.atnzvdev.data.mapper

import com.atnzvdev.data.network.dto.CourseDTO
import com.atnzvdev.domain.model.Course
import javax.inject.Inject

class CourseMapper @Inject constructor() {
    fun map(dto: CourseDTO): Course {
        return Course(
            id = dto.id ?: -1,
            title = dto.title.orEmpty(),
            text = dto.text.orEmpty(),

            price = dto.price?.replace("\\s+".toRegex(), "")?.toIntOrNull() ?: 0,

            rating = dto.rate?.toFloatOrNull() ?: 0.0f,

            startDate = dto.startDate.orEmpty(),
            isFavorite = dto.hasLike ?: false,
            publishDate = dto.publishDate.orEmpty()
        )
    }

    fun mapList(dtos: List<CourseDTO>): List<Course> {
        return dtos.map { map(it) }
            .filter { it.id != -1 }
    }
}