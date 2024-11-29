package com.lalapanbulaos.nutric.features.healthinfo.data.model

import com.google.gson.annotations.SerializedName

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

  @SerializedName("activity_level")
  val activityLevel: ActivityLevel,

  @SerializedName("allergies_name")
  val allergiesName: List<String>
)

