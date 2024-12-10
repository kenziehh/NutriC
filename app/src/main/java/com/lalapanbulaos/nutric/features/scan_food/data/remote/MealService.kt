package com.lalapanbulaos.nutric.features.scan_food.data.remote

import com.lalapanbulaos.nutric.core.models.MealLog
import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.scan_food.data.model.MealLogRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MealService {
    @POST("meal")
    suspend fun createMealLog(
        @Body mealLog: MealLogRequest
    ): Response<ApiResponse<MealLog>>
}