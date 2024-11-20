package com.lalapanbulaos.nutric.features.auth.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.User
import com.lalapanbulaos.nutric.features.auth.data.models.SignUpRequest
import com.lalapanbulaos.nutric.features.auth.data.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesManager: UserPreferencesManager
) {

    suspend fun execute(username: String, password: String): Result<Unit> {
        val signUpRequest = SignUpRequest(username, password)

        return try {
            // Attempt to sign in
            val result = authRepository.signUp(signUpRequest)

            // Handle the result
            val response = result.getOrThrow()

            // Create user object
            val user = User(response.user.id, response.user.username)

            // Save user data and access token in preferences
            userPreferencesManager.saveUser(user)
            userPreferencesManager.saveAccessToken(response.access_token)

            // Return success
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("SignUpUseCase", "Error during sign-in: ${e.message}", e)
            Result.failure(e)
        }
    }
}
