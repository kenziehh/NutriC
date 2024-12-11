package com.lalapanbulaos.nutric.features.meal.usecase

import android.util.Log
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetDailyTargetUseCase
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class GetMacroTargetPercentageAverage @Inject constructor(
    private val getDailyTargetUseCase: GetDailyTargetUseCase,
    private val getTotalMacroNutrientUseCase: GetTotalMacroNutrientUseCase
) {
    suspend fun execute(meals: List<MealResponse>): Result<MacroTargetAverage> {
        Log.d("GetMacroTargetPercentageAverage", "Executing with meals: $meals")

        if (meals.isEmpty()) {
            Log.d("GetMacroTargetPercentageAverage", "No meals to calculate average")
            return Result.success(MacroTargetAverage.zero())
        }

        // Get daily targets
        val dailyTargetResult = getDailyTargetUseCase.execute()
        val dailyTarget = dailyTargetResult.getOrElse {
            Log.e("GetMacroTargetPercentageAverage", "Unable to retrieve daily nutritional targets: ${it.message}")
            return Result.failure(IllegalStateException("Unable to retrieve daily nutritional targets: ${it.message}"))
        }
        Log.d("GetMacroTargetPercentageAverage", "Daily Target: $dailyTarget")

        // Group meals by date
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val mealsByDate = meals.mapNotNull { meal ->
            try {
                val date = isoFormat.parse(meal.created_at)!!
                val truncatedDate = dateFormat.parse(dateFormat.format(date))!!
                truncatedDate to meal
            } catch (e: Exception) {
                Log.e("GetMacroTargetPercentageAverage", "Error parsing date: ${e.message}")
                null
            }
        }.groupBy({ it.first }, { it.second })

        if (mealsByDate.isEmpty()) {
            Log.d("GetMacroTargetPercentageAverage", "No meals to calculate average")
            return Result.success(MacroTargetAverage.zero())
        }

        Log.d("GetMacroTargetPercentageAverage", "Meals grouped by date: $mealsByDate")

        val dailyMacro: List<MacroTargetAverage> = mealsByDate.map { (date, dailyMeals) ->
            Log.d("GetMacroTargetPercentageAverage", "Processing date: $date with meals: $dailyMeals")

            val foodMacroNutrientResult = getTotalMacroNutrientUseCase.executeWithMeals(dailyMeals)

            val foodMacroNutrient = foodMacroNutrientResult.getOrElse {
                Log.e("GetMacroTargetPercentageAverage", "Error calculating total macro nutrient: ${it.message}")
                return@map MacroTargetAverage.zero()
            }

            Log.d("GetMacroTargetPercentageAverage", "Total Macro Nutrient: $foodMacroNutrient")

            val percentage = MacroTargetPercentage(
                calories = calculatePercentage(foodMacroNutrient.calories, dailyTarget.calories.toFloat()),
                protein = calculatePercentage(foodMacroNutrient.protein, dailyTarget.protein.toFloat()),
                fat = calculatePercentage(foodMacroNutrient.fat, dailyTarget.fat.toFloat()),
                carbohydrates = calculatePercentage(foodMacroNutrient.carbohydrates, dailyTarget.carbohydrates.toFloat()),
                fiber = calculatePercentage(foodMacroNutrient.fiber, dailyTarget.fiber.toFloat()),
                sugar = calculatePercentage(foodMacroNutrient.sugar, dailyTarget.sugar.toFloat())
            )

            val values = MacroTargetValues(
                calories = foodMacroNutrient.calories,
                protein = foodMacroNutrient.protein,
                fat = foodMacroNutrient.fat,
                carbohydrates = foodMacroNutrient.carbohydrates,
                fiber = foodMacroNutrient.fiber,
                sugar = foodMacroNutrient.sugar
            )

            MacroTargetAverage(
                averagePercentage = percentage,
                averageValues = values
            )
        }

        Log.d("GetMacroTargetPercentageAverage", "Daily macro targets: $dailyMacro")

        // Calculate average
        val averageMacroTargetPercentage = MacroTargetPercentage(
            calories = dailyMacro.map { it.averagePercentage.calories }.average().toFloat(),
            protein = dailyMacro.map { it.averagePercentage.protein }.average().toFloat(),
            fat = dailyMacro.map { it.averagePercentage.fat }.average().toFloat(),
            carbohydrates = dailyMacro.map { it.averagePercentage.carbohydrates }.average().toFloat(),
            fiber = dailyMacro.map { it.averagePercentage.fiber }.average().toFloat(),
            sugar = dailyMacro.map { it.averagePercentage.sugar }.average().toFloat()
        )

        val averageMacroTargetValue = MacroTargetValues(
            calories = dailyMacro.map { it.averageValues.calories }.average().toFloat(),
            protein = dailyMacro.map { it.averageValues.protein }.average().toFloat(),
            fat = dailyMacro.map { it.averageValues.fat }.average().toFloat(),
            carbohydrates = dailyMacro.map { it.averageValues.carbohydrates }.average().toFloat(),
            fiber = dailyMacro.map { it.averageValues.fiber }.average().toFloat(),
            sugar = dailyMacro.map { it.averageValues.sugar }.average().toFloat()
        )

        return Result.success(MacroTargetAverage(
            averagePercentage = averageMacroTargetPercentage,
            averageValues = averageMacroTargetValue
        ))
    }

    private fun calculatePercentage(actual: Float, target: Float): Float {
        return if (target > 0) (actual / target * 100f) else 0f
    }
}


data class MacroTargetPercentage(
    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val sugar: Float
) {
    companion object {
        fun zero() = MacroTargetPercentage(0f, 0f, 0f, 0f, 0f, 0f)
    }
}

data class MacroTargetAverage(
    val averagePercentage: MacroTargetPercentage,
    val averageValues: MacroTargetValues
) {
    companion object {
        fun zero() = MacroTargetAverage(
            averagePercentage = MacroTargetPercentage.zero(),
            averageValues = MacroTargetValues.zero()
        )
    }
}

data class MacroTargetValues(
    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val sugar: Float
) {
    companion object {
        fun zero() = MacroTargetValues(0f, 0f, 0f, 0f, 0f, 0f)
    }
}
