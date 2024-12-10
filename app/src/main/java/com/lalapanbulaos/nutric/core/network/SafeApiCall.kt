package com.lalapanbulaos.nutric.core.network

import com.lalapanbulaos.nutric.core.data.exception.ApiException
import com.lalapanbulaos.nutric.core.data.exception.AuthenticationException
import com.lalapanbulaos.nutric.core.data.exception.UnexpectedException
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import kotlinx.coroutines.flow.first
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<ApiResponse<T>>
): Result<T> {
    return try {
        val response = apiCall()

        if (!response.isSuccessful) {
            return Result.failure(
                ApiException(response.code(), response.message())
            )
        }

        val apiResponse = response.body()
            ?: return Result.failure(ApiException(0, "Empty response body"))

        if (!apiResponse.success) {
            return Result.failure(ApiException(0, apiResponse.message))
        }

        Result.success(apiResponse.data)
    } catch (e: Exception) {
        when (e) {
            is AuthenticationException,
            is ApiException -> Result.failure(e)
            else -> Result.failure(UnexpectedException(e))
        }
    }
}