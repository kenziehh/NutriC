package com.lalapanbulaos.nutric.features.scan_food.usecase

import android.util.Log
import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodNamePredictResponse
import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodPredictRepository
import java.io.File
import javax.inject.Inject

class PredictFoodNameUseCase @Inject constructor(
    private val foodPredictRepository: FoodPredictRepository
) {
    suspend fun execute(image: File): Result<FoodNamePredictResponse> {
        Log.d("PredictFoodNameUseCase", "execute called")
        return foodPredictRepository.predictFoodName(image)
    }
}
