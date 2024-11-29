package com.lalapanbulaos.nutric.core.models

data class FoodMicroNutrient(
    val id: String,
    val foodId: String,
    val vitaminA: Float?,  // mcg
    val vitaminC: Float?,  // mg
    val calcium: Float?,   // mg
    val iron: Float?,      // mg
    val magnesium: Float?, // mg
    val potassium: Float?, // mg
    val zinc: Float?       // mg
)

