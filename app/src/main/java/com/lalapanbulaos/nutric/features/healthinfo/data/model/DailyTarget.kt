package com.lalapanbulaos.nutric.features.healthinfo.data.model

import com.google.gson.annotations.SerializedName

data class DailyTarget(
    val id: String,
    @SerializedName("user_id")
    val userId: String,
    val calories: Double,
    val protein: Double,
    val fat: Double,
    val carbohydrates: Double,
    val fiber: Double,
    val sugar: Double
)