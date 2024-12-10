package com.lalapanbulaos.nutric.features.meal.data.repository

import android.util.Log
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import com.lalapanbulaos.nutric.features.meal.data.remote.MealService
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealService: MealService) {
    suspend fun getMeals(filterBy: String?=null): Result<List<MealResponse>> {
        val response = mealService.getMeals(filterBy = filterBy)

        Log.d("MealRepository", "Response code: ${response.code()}")
        Log.d("MealRepository", "Response body: ${response.body()}")

        // Check if the response body is null
        if (response.body() == null) {
            Log.e("MealRepository", "Response body is null!")
        }

        return try {
            Result.success(
                response.body()?.data ?: throw Exception("Response body is null")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}