//package com.lalapanbulaos.nutric.features.meal.usecase
//
//import android.util.Log
//import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
//import com.lalapanbulaos.nutric.features.meal.data.repository.MealRepository
//import javax.inject.Inject
//
//class GetTotalCaloriesUseCase @Inject constructor(private val mealRepository: MealRepository) {
//    suspend fun execute(): Result<List<MealResponse>>{
//        try {
//            val response = mealRepository.getMeals()
//            var totalCalories=0
//            response.onSuccess {
//                meals->{
//                    totalCalories +=
//                    Log.d(meals)
//            }
//            }
//
//        }
//        return null
//    }
//}