package com.lalapanbulaos.nutric.features.healthinfo.data.model

data class HealthInfo(
  val id: String,
  val userId: String,
  val age: Int,
  val height: Double,
  val weight: Double,
  val activityLevel: ActivityLevel,
  val allergies: List<Allergy>
)

enum class ActivityLevel {
  LIGHT,
  MODERATE,
  ACTIVE,
  VERY_ACTIVE
}

data class HealthInfoRequest(
  val age: Int,
  val height: Double,
  val weight: Double,
  val activityLevel: ActivityLevel,
  val allergies: List<String>
)