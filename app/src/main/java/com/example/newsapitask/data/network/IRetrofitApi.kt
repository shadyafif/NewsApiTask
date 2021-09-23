package com.example.newsapitask.data.network

import com.example.newsapitask.data.model.NewsDetails.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitApi {
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsModel
}