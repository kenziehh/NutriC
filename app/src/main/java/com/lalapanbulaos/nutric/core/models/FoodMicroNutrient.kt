package com.lalapanbulaos.nutric.core.models

import com.google.gson.annotations.SerializedName

data class FoodMicroNutrient(
    val id: String,
    val foodId: String,

    @SerializedName("vitamin_a")
    val vitaminA: Float?,  // mcg

    @SerializedName("vitamin_c")
    val vitaminC: Float?,  // mg
    val calcium: Float?,   // mg
    val iron: Float?,      // mg
    val magnesium: Float?, // mg
    val potassium: Float?, // mg
    val zinc: Float?       // mg
)

