package com.lalapanbulaos.nutric.features.meal.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.models.FoodMacroNutrient
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import com.lalapanbulaos.nutric.features.meal.data.repository.MealRepository
import javax.inject.Inject

class GetTotalMacroNutrientUseCase @Inject constructor(private val mealRepository: MealRepository) {

    suspend fun execute(meals: List<MealResponse>): Result<FoodMacroNutrient> {
        return try {
            val mealResponsesResult = mealRepository.getMeals()

            if (mealResponsesResult.isSuccess) {
                val mealResponses = mealResponsesResult.getOrNull() ?: emptyList()

                var totalCalories = 0f
                var totalProtein = 0f
                var totalFat = 0f
                var totalCarbohydrates = 0f

                mealResponses.forEach { meal ->
                    meal.food.foodMacroNutrient?.let { macro ->
                        totalCalories += macro.calories
                        totalProtein += macro.protein
                        totalFat += macro.fat
                        totalCarbohydrates += macro.carbohydrates
                    }
                }

                Result.success(
                    FoodMacroNutrient(
                        id = "total",
                        foodId = "aggregated",
                        calories = totalCalories,
                        protein = totalProtein,
                        fat = totalFat,
                        carbohydrates = totalCarbohydrates
                    )
                )
            } else {
                val error = mealResponsesResult.exceptionOrNull()
                Log.e("GetTotalMacroNutrient", "Failed to fetch meals", error)
                Result.failure(error ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e("GetTotalMacroNutrient", "Unexpected error occurred", e)
            Result.failure(e)
        }
    }
}