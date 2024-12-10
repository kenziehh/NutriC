package com.lalapanbulaos.nutric.core.models

import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.SerializedName

data class FoodMacroNutrient(
    val id: String,
    @SerializedName("food_id")
    val foodId: String,

    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val sugar: Float
)

