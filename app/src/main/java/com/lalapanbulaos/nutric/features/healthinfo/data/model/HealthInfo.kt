package com.lalapanbulaos.nutric.features.healthinfo.data.model

import com.google.gson.annotations.SerializedName

enum class ActivityLevel {
  @SerializedName("sedentary")
  SEDENTARY,

  @SerializedName("lightly active")
  LIGHTLY_ACTIVE,

  @SerializedName("moderately active")
  MODERATELY_ACTIVE,

  @SerializedName("very active")
  VERY_ACTIVE
}

fun describeActivityLevel(activityLevel: ActivityLevel): String {
  return when (activityLevel) {
    ActivityLevel.SEDENTARY -> "Sedentary"
    ActivityLevel.LIGHTLY_ACTIVE -> "Lightly Active"
    ActivityLevel.MODERATELY_ACTIVE -> "Moderately Active"
    ActivityLevel.VERY_ACTIVE -> "Very Active"
  }
}

data class HealthInfoRequest(
  val gender: String,
  val age: Int,
  val height: Double,
  val weight: Double,

  @SerializedName("activity_level")
  val activityLevel: ActivityLevel,

  @SerializedName("allergies_name")
  val allergiesName: List<String>
)

