package com.atnzvdev.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.atnzvdev.data.dao.CourseDao
import com.atnzvdev.data.local.entity.CourseEntity

@Database(
    entities = [CourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao

}