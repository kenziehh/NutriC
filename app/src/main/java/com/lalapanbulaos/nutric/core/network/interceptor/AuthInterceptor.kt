package com.lalapanbulaos.nutric.core.network.interceptor

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (private val userPreferencesManager: UserPreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = runBlocking {
            userPreferencesManager.getBearerToken()
        }


        Log.d("AuthInterceptor", "Bearer Token: $bearerToken")

        val originalRequest = chain.request()
        val modifiedRequest = if (bearerToken != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", bearerToken)
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(modifiedRequest)

        if (response.code == 401) {
            runBlocking {
                userPreferencesManager.clearAccessToken()
            }
        }

        return response
    }
}
