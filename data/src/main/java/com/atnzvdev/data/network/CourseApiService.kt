package com.atnzvdev.data.network

import com.atnzvdev.data.network.dto.CoursesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseApiService {
    @GET("u/0/uc")
    suspend fun getCourses(
        @Query("id") fileId: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") exportMode: String = "download"
    ): CoursesResponseDto
}