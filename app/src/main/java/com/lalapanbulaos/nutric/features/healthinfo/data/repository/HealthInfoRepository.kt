package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.DailyTarget
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
    private val healthInfoService: HealthInfoService
  ) {
    suspend fun getHealthInfo(): Result<HealthInfo> {
      return try {
          val response = healthInfoService.getHealthInfo()

          Result.success(
            response.body()?.data ?: throw Exception("Response body is null")
          )
      } catch (e: Exception) {
        Result.failure(e)
      }
    }

      suspend fun getDailyTarget(): Result<DailyTarget> {
          return try {
              val response = healthInfoService.getDailyTarget()

              Result.success(
                  response.body()?.data ?: throw Exception("Response body is null")
              )
          } catch (e: Exception) {
              Result.failure(e)
          }
      }

    suspend fun createHealthInfo(healthInfoRequest: HealthInfoRequest): Result<HealthInfo> {
      return try {
          val response = healthInfoService.createHealthInfo(healthInfoRequest)

          if (response.isSuccessful) {
            Result.success(
              response.body()?.data ?: throw Exception("Response body is null")
            )
          } else {
            Result.failure(Throwable(response.body()?.message ?: "Unknown error"))
          }
      } catch (e: Exception) {
        Result.failure(e)
      }
    }

  }

