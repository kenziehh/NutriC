package com.lalapanbulaos.nutric.core.data.repository

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.core.network.safeApiCall
import retrofit2.Response

abstract class BaseRepository(
) {
    protected suspend fun <T> executeApiCall(
        apiCall: suspend () -> Response<ApiResponse<T>>
    ): Result<T> = safeApiCall(apiCall)
}