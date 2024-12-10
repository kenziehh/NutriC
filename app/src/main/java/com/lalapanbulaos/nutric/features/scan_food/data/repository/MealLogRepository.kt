package com.lalapanbulaos.nutric.features.scan_food.data.repository

import android.util.Log
import com.lalapanbulaos.nutric.core.data.repository.BaseRepository
import com.lalapanbulaos.nutric.core.models.MealLog
import com.lalapanbulaos.nutric.features.scan_food.data.model.MealLogRequest
import com.lalapanbulaos.nutric.features.scan_food.data.remote.MealService
import javax.inject.Inject

class MealLogRepository @Inject constructor(
    private val mealService: MealService
): BaseRepository() {
    suspend fun createMealLog(mealLog: MealLogRequest): Result<MealLog> {
        Log.d("MealLogRepository", "createMealLog called")
        return executeApiCall {
            mealService.createMealLog(mealLog)
        }
    }
}