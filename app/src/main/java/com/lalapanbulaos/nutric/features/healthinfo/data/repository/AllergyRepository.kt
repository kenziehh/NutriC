package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import com.lalapanbulaos.nutric.core.models.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.AllergyService
import javax.inject.Inject

class AllergyRepository @Inject constructor(private val allergyService: AllergyService) {
  suspend fun getAllergies(): Result<List<Allergy>> {
    return try {
      val response = allergyService.getAllergies()

      Result.success(
        response.body()?.data ?: throw Exception("Response body is null")
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}