package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfoRequest
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.HealthInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HealthInfoRepository @Inject constructor(
  private val healthInfoService: HealthInfoService,
  private val userPreferencesManager: UserPreferencesManager
) {
  fun getHealthInfo(): Flow<HealthInfo> = flow {
    try {
      val accessToken = userPreferencesManager.accessToken.first()
      val authToken = "Bearer $accessToken"

      Log.d("HealthInfoRepository", "Token: $authToken")

      val response = healthInfoService.getHealthInfo(token = authToken)

      Log.d("HealthInfoRepository", "Response: $response")
    } catch (e: Exception) {
      throw Exception("Failed to fetch health info: ${e.message}", e)
    }
  }

//  suspend fun createHealthInfo(healthInfoRequest: HealthInfoRequest): Result<HealthInfo> {
//    return try {
//      val accessToken = userPreferencesManager.accessToken
//      val response = healthInfoService.createHealthInfo(healthInfoRequest, "Bearer $accessToken")
//
//      if (response.isSuccessful) {
//        response.body()?.data?.let { healthInfo ->
//          Result.success(healthInfo)
//        } ?: Result.failure(Exception("Response body is null"))
//      } else {
//        Result.failure(Exception("API call failed with code: ${response.code()}"))
//      }
//    } catch (e: Exception) {
//      Result.failure(Exception("An error occurred: ${e.message}", e))
//    }
//  }
}