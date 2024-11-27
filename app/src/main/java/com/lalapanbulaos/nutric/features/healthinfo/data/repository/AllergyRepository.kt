package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.AllergyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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