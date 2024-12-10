package com.lalapanbulaos.nutric.features.scan_food.data.remote

import com.lalapanbulaos.nutric.core.models.Food
import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodNamePredictResponse
import com.lalapanbulaos.nutric.features.scan_food.usecase.PredictNutritionRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FoodPredictService {
    @Multipart
    @POST("food/predict-food")
    suspend fun predictFoodName(@Part foodImage: MultipartBody.Part): Response<ApiResponse<FoodNamePredictResponse>>

    @POST("food/predict-nutrition")
    suspend fun predictFoodNutrition(@Body predictNutritionRequest: PredictNutritionRequest): Response<ApiResponse<Food>>
}