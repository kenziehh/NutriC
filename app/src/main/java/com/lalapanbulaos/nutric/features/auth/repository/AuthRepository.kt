package com.lalapanbulaos.nutric.features.auth.repository

import com.lalapanbulaos.nutric.features.auth.data.models.AuthResponse
import com.lalapanbulaos.nutric.features.auth.data.models.SignInRequest
import com.lalapanbulaos.nutric.features.auth.data.models.SignUpRequest
import com.lalapanbulaos.nutric.features.auth.data.remote.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: AuthService) {

    suspend fun signIn(signInRequest: SignInRequest): Result<AuthResponse> {
        return try {
            val response = apiService.signIn(signInRequest)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data!!)
            } else {
                Result.failure(Exception("Login failed: ${response.body()?.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Result<AuthResponse> {
        return try {
            val response = apiService.signUp(signUpRequest)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.body()?.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}