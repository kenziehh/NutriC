package com.lalapanbulaos.nutric.features.meal.data.remote

import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {
    @GET("meal")
    suspend fun getMeals(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("filterBy") filterBy: String? = null
    ): Response<ApiResponse<List<MealResponse>>>
}