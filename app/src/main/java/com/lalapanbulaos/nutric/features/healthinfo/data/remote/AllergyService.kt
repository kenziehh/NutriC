package com.lalapanbulaos.nutric.features.healthinfo.data.remote

import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy
import retrofit2.Response
import retrofit2.http.GET

interface AllergyService {
  @GET("allergy")
  suspend fun getAllergies(): Response<ApiResponse<List<Allergy>>>
}