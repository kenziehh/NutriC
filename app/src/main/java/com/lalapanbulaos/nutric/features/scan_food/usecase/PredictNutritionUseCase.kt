package com.lalapanbulaos.nutric.features.scan_food.usecase

import com.lalapanbulaos.nutric.core.models.Food
import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodPredictRepository
import javax.inject.Inject

class PredictNutritionUseCase @Inject constructor(
    private val foodPredictRepository: FoodPredictRepository
) {
    suspend fun execute(foodName: String): Result<Food> {
        val request = PredictNutritionRequest(foodName)
        return foodPredictRepository.predictFoodNutrition(request)
    }
}

data class PredictNutritionRequest(
    val foodName: String
)