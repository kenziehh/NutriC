package com.lalapanbulaos.nutric.features.scan_food.data.model

data class MealLogRequest(
    val foodId: String,
    val servingSize: Int,
    val servingUnit: String,
)
