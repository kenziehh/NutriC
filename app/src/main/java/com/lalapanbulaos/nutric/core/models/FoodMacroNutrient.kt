package com.lalapanbulaos.nutric.core.models

data class FoodMacroNutrient(
    val id: String,
    val foodId: String,
    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float
)

