package com.lalapanbulaos.nutric.core.models

import com.lalapanbulaos.nutric.features.healthinfo.data.model.ActivityLevel

data class HealthInfo(
    val id: String,
    val userId: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val activityLevel: ActivityLevel,
    val allergies: List<Allergy>
)
