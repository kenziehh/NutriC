package com.lalapanbulaos.nutric.features.meal.data.models

import com.lalapanbulaos.nutric.core.models.Food

data class MealResponse(
    val id: String,
    val user_id: String,
    val serving_size: Int,
    val serving_unit: String,
    val created_at: String,
    val updated_at: String,
    val food: Food
)
