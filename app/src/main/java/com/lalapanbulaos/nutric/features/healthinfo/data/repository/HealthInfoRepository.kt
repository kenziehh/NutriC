package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfoRequest
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.HealthInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HealthInfoRepository @Inject constructor(
  private val healthInfoService: HealthInfoService,
  private val userPreferencesManager: UserPreferencesManager
) {
  fun getHealthInfo(): Flow<HealthInfo> {
    try {
      val response = healthInfoService.getHealthInfo("Bearer ${userPreferencesManager.accessToken}")
      if (response.isSuccessful) {
        response.body()?.data?.let { healthInfo ->
          return flow { emit(healthInfo) }
        } ?: throw Exception("Response body is null")
      } else {
        throw Exception("API call failed with code: ${response.code()}")
      }
    } catch (e: Exception) {
      throw e
    }
  }

  suspend fun createHealthInfo(healthInfoRequest: HealthInfoRequest): HealthInfo {

    val response = healthInfoService.createHealthInfo(
      healthInfoRequest, "Bearer ${userPreferencesManager.accessToken}"
    )

    if (response.isSuccessful) {
      return response.body()?.data ?: throw Exception("Response body is null")
    } else {
      throw Exception("API call failed with code: ${response.code()}")
    }

  }
}