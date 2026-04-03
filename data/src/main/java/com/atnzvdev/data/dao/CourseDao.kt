package com.atnzvdev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atnzvdev.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    fun getAllCoursesFlow(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE isFavorite = 1")
    fun getFavoriteCoursesFlow(): Flow<List<CourseEntity>>

    @Query("SELECT id FROM courses WHERE isFavorite = 1")
    suspend fun getFavoriteCourseIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<CourseEntity>)

    @Query("UPDATE courses SET isFavorite = :isFavorite WHERE id = :courseId")
    suspend fun updateFavoriteStatus(courseId: Int, isFavorite: Boolean)
}