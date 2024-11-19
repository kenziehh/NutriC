package com.lalapanbulaos.nutric.features.auth.usecase

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.User
import com.lalapanbulaos.nutric.features.auth.data.models.SignInRequest
import com.lalapanbulaos.nutric.features.auth.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesManager: UserPreferencesManager
) {

    suspend fun execute(username: String, password: String): Result<Unit> {
        val signInRequest = SignInRequest(username, password)

        return try {
            // Attempt to sign in
            val result = authRepository.signIn(signInRequest)

            // Handle the result
            val response = result.getOrThrow()

            // Create user object
            val user = User(
                response.userId,
                response.username
            )

            // Save user data and access token in preferences
            userPreferencesManager.saveUser(user)
            userPreferencesManager.saveAccessToken(response.access_token)

            // Return success
            Result.success(Unit)
        } catch (e: Exception) {
            // Return failure in case of an exception
            Result.failure(e)
        }
    }
}
