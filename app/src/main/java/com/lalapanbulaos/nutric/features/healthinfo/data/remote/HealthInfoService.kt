package com.lalapanbulaos.nutric.features.healthinfo.data.remote

import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface HealthInfoService {
  @GET("health-info")
  fun getHealthInfo(
    @Header("Authorization") token: String
  ): Response<ApiResponse<HealthInfo>>

  @POST("health-info")
  suspend fun createHealthInfo(@Body request: HealthInfoRequest, @Header("Authorization") token: String): Response<ApiResponse<HealthInfo>>

  @PUT("health-info")
  suspend fun updateHealthInfo(@Body request: HealthInfoRequest, @Header("Authorization") token: String): Response<ApiResponse<HealthInfo>>
}