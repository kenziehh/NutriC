package com.lalapanbulaos.nutric.core.models

data class MealLog(
    val id: String,
    val userId: String,
    val foodId: String,
    val servingSize: Float,
    val servingUnit: String,
    val createdAt: String, // ISO8601 format
    val updatedAt: String
)

