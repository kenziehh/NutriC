package com.lalapanbulaos.nutric.core.network.interceptor

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (private val userPreferencesManager: UserPreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            runBlocking {
                userPreferencesManager.clearAccessToken()
            }
        }

        return response
    }
}
