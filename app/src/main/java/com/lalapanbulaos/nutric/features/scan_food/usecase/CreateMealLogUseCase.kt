package com.lalapanbulaos.nutric.features.scan_food.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.models.MealLog
import com.lalapanbulaos.nutric.features.scan_food.data.model.MealLogRequest
import com.lalapanbulaos.nutric.features.scan_food.data.repository.MealLogRepository
import javax.inject.Inject

class CreateMealLogUseCase @Inject constructor(
    private val mealLogRepository: MealLogRepository
) {
    suspend fun execute(mealId: String): Result<MealLog> {
        val mealLog = MealLogRequest(
            foodId = mealId,
            servingSize = 1,
            servingUnit = "default"
        )
        Log.d("CreateMealLogUseCase", "execute called")
        return mealLogRepository.createMealLog(mealLog)
    }
}