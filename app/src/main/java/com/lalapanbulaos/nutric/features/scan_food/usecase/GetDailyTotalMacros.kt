package com.lalapanbulaos.nutric.features.scan_food.usecase

import com.lalapanbulaos.nutric.core.models.FoodMacroNutrient
import com.lalapanbulaos.nutric.features.meal.data.repository.MealRepository
import javax.inject.Inject

class GetDailyTotalMacros @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend fun execute(): Result<FoodMacroNutrient> {
        return try {
            val result = mealRepository.getMeals("daily")

            if (result.isSuccess) {
                val meals = result.getOrNull() ?: emptyList()

                // If there are no meals, return a FoodMacroNutrient with all values set to zero.
                if (meals.isEmpty()) {
                    val emptyMacroNutrient = FoodMacroNutrient(
                        id = "total",
                        foodId = "aggregated",
                        calories = 0f,
                        protein = 0f,
                        fat = 0f,
                        carbohydrates = 0f,
                        fiber = 0f,
                        sugar = 0f
                    )
                    return Result.success(emptyMacroNutrient)
                }

                var totalCalories = 0f
                var totalProtein = 0f
                var totalFat = 0f
                var totalCarbohydrates = 0f
                var totalFibers = 0f
                var totalSugars = 0f

                for (mealResponse in meals) {
                    mealResponse.food.foodMacroNutrient?.let { macro ->
                        totalCalories += macro.calories
                        totalProtein += macro.protein
                        totalFat += macro.fat
                        totalCarbohydrates += macro.carbohydrates
                        totalFibers += macro.fiber
                        totalSugars += macro.sugar
                    }
                }

                val totalMacroNutrient = FoodMacroNutrient(
                    id = "total",
                    foodId = "aggregated",
                    calories = totalCalories,
                    protein = totalProtein,
                    fat = totalFat,
                    carbohydrates = totalCarbohydrates,
                    fiber = totalFibers,
                    sugar = totalSugars
                )

                Result.success(totalMacroNutrient)
            } else {
                Result.failure(result.exceptionOrNull() ?: Error("Unexpected error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}