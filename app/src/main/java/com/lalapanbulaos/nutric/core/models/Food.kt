package com.lalapanbulaos.nutric.core.models

import com.google.gson.annotations.SerializedName


data class Food(
    val id: String,
    val name: String,
    val category: String,
    @SerializedName("food_macronutrient")
    val foodMacroNutrient: FoodMacroNutrient?,
    @SerializedName("food_micronutrient")
    val foodMicroNutrient: FoodMicroNutrient?,

    val allergens: List<Allergy>,
)

