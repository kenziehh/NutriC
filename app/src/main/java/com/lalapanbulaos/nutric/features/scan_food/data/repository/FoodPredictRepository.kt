package com.lalapanbulaos.nutric.features.scan_food.data.repository

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.data.repository.BaseRepository
import com.lalapanbulaos.nutric.core.models.Food
import com.lalapanbulaos.nutric.features.scan_food.data.remote.FoodPredictService
import com.lalapanbulaos.nutric.features.scan_food.usecase.PredictNutritionRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class FoodPredictRepository @Inject constructor(
    private val foodPredictService: FoodPredictService,
): BaseRepository() {
    suspend fun predictFoodName(image: File): Result<FoodNamePredictResponse> {
        return executeApiCall {
            Log.d("FoodPredictRepository", "predictFoodName called")

            val filePart = MultipartBody.Part.createFormData(
                "foodImage",
                image.name,
                image.asRequestBody("image/*".toMediaTypeOrNull())
            )

            Log.d("FoodPredictRepository", "File part created: $filePart")

            foodPredictService.predictFoodName(filePart)
        }
    }

    suspend fun predictFoodNutrition(predictNutritionRequest: PredictNutritionRequest): Result<Food> {
        return executeApiCall {
            foodPredictService.predictFoodNutrition(predictNutritionRequest)
        }
    }
}

data class FoodNamePredictResponse(
   val foodName: String
)