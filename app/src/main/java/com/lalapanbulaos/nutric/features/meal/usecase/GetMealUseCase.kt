package com.lalapanbulaos.nutric.features.meal.usecase

import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import com.lalapanbulaos.nutric.features.meal.data.repository.MealRepository
import javax.inject.Inject

class GetMealUseCase @Inject constructor(private val mealRepository: MealRepository) {
    suspend fun execute(): Result<List<MealResponse>>{
        return mealRepository.getMeals()
    }

}