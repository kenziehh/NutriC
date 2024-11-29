package com.lalapanbulaos.nutric.core.models

data class Food(
    val id: String,
    val name: String,
    val category: String,
    val foodMacroNutrient: FoodMacroNutrient?,
    val foodMicroNutrient: FoodMicroNutrient?,
    val allergens: List<Allergy>,
    val mealLogs: List<MealLog>,
    val scanHistories: List<ScanHistory>
)

