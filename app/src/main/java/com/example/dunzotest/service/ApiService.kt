package com.todayq.official.service

import com.example.shipsytest.model.PostModel
import retrofit2.http.*

interface ApiService {

    @GET("rest/")
    suspend fun getPosts(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("tags") tags: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String,
        @Query("per_page") per_page: String,
        @Query("page") page: String
        ): PostModel
}