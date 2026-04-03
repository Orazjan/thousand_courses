package com.atnzvdev.data.repository

import com.atnzvdev.data.dao.CourseDao
import com.atnzvdev.data.local.entity.CourseEntity
import com.atnzvdev.data.local.entity.toDomain
import com.atnzvdev.data.local.entity.toEntity
import com.atnzvdev.data.mapper.CourseMapper
import com.atnzvdev.data.network.CourseApiService
import com.atnzvdev.domain.model.Course
import com.atnzvdev.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApiService,
    private val dao: CourseDao,
    private val mapper: CourseMapper
) : CourseRepository {

    override fun getCoursesStream(): Flow<List<Course>> {
        return dao.getAllCoursesFlow().map { list -> list.map { it.toDomain() } }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return dao.getFavoriteCoursesFlow().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun fetchCoursesFromServer(): Result<Unit> {
        return try {
            val remoteCourses = loadRemoteCourses()
            val mergedCourses = mergeWithLocalFavorites(remoteCourses)
            dao.insertAll(mergedCourses)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavoriteStatus(courseId: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(courseId, isFavorite)
    }

    private suspend fun loadRemoteCourses(): List<Course> {
        return mapper.mapList(api.getCourses().courses)
    }

    private suspend fun mergeWithLocalFavorites(courses: List<Course>): List<CourseEntity> {
        val localFavorites = dao.getFavoriteCourseIds().toSet()

        return courses.map { course ->
            val mergedFavorite = course.isFavorite || localFavorites.contains(course.id)
            course.toEntity(hasLocalLike = mergedFavorite)
        }
    }
}