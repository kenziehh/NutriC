package com.lalapanbulaos.nutric.features.auth.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.User
import com.lalapanbulaos.nutric.features.auth.data.models.SignInRequest
import com.lalapanbulaos.nutric.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

class SignInUseCase @Inject constructor(
  private val authRepository: AuthRepository,
  private val userPreferencesManager: UserPreferencesManager
) {

    suspend fun execute(username: String, password: String): Result<Unit> {
        val signInRequest = SignInRequest(username, password)

        return try {
            userPreferencesManager.clearAccessToken()
            userPreferencesManager.clearUser()

            val result = authRepository.signIn(signInRequest)

            Log.d("SignInUseCase", "Sign-in result: $result")

            // Handle the result
            val response = result.getOrThrow()

            Log.d("SignInUseCase", "Sign-in response: $response")

            // Create user object
            val user = User(response.user.id, response.user.username)

            userPreferencesManager.saveUser(user)
            userPreferencesManager.saveAccessToken(response.access_token)

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("SignInUseCase", "Error during sign-in: ${e.message}", e)
            Result.failure(e)
        }
    }
}
