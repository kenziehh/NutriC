package com.lalapanbulaos.nutric.features.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.HealthInfo
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import com.lalapanbulaos.nutric.features.auth.usecase.SignUpUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetHealthInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val userPreferencesManager: UserPreferencesManager,
    private val getHealthInfoUseCase: GetHealthInfoUseCase
) : ViewModel() {
    // Separate state for input validation and UI
    private val _inputState = MutableStateFlow(AuthInputState())
    val inputState: StateFlow<AuthInputState> = _inputState.asStateFlow()

    // Separate state for authentication mode
    private val _authMode = MutableStateFlow(AuthMode.SIGN_IN)
    val authMode: StateFlow<AuthMode> = _authMode.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _healthInfo = MutableStateFlow<HealthInfo?>(null)
    val healthInfo: StateFlow<HealthInfo?> = _healthInfo.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            val token = userPreferencesManager.accessToken.first()

            if (token != null) {
                Log.d("AuthViewModel", "Token found: $token")
                loadHealthInfo()
                if (_healthInfo.value == null) {
                    _authState.value = AuthState.RequiresHealthInfo
                } else {
                    _authState.value = AuthState.Authenticated
                }
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    private suspend fun loadHealthInfo() {
        getHealthInfoUseCase.execute().onSuccess {
            _healthInfo.value = it
        }.onFailure {
            Log.e("AuthViewModel", "Error loading health info", it)
        }

    }

    fun updateUsername(username: String) {
        _inputState.update { it.copy(username = username) }
    }

    fun updatePassword(password: String) {
        _inputState.update { it.copy(password = password) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _inputState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun switchAuthMode() {
        val mode = if (_authMode.value == AuthMode.SIGN_IN) {
            AuthMode.SIGN_UP
        } else {
            AuthMode.SIGN_IN
        }
        _authMode.value = mode
        _inputState.value = AuthInputState()
    }

    fun authenticate() {
        val validationError = validateInput()
        if (validationError != null) {
            _authState.value = AuthState.Error(validationError)
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = when (_authMode.value) {
                AuthMode.SIGN_UP -> signUpUseCase.execute(
                    _inputState.value.username,
                    _inputState.value.password
                )

                AuthMode.SIGN_IN -> signInUseCase.execute(
                    _inputState.value.username,
                    _inputState.value.password
                )
            }

            result.onSuccess {
                checkAuthState()
            }.onFailure {
                _authState.value = AuthState.Error(it.message ?: "Unknown error")
            }
        }
    }

    suspend fun logout() {
        userPreferencesManager.clearUser()
        userPreferencesManager.clearAccessToken()
        _authState.value = AuthState.Unauthenticated
        Log.d("AuthViewModel", "Logout successful")
    }

    private fun validateInput(): String? {
        val currentState = _inputState.value
        return when {
            currentState.username.isEmpty() -> "Username cannot be empty"
            currentState.password.isEmpty() -> "Password cannot be empty"
            _authMode.value == AuthMode.SIGN_UP &&
                    currentState.password != currentState.confirmPassword ->
                "Passwords do not match"

            else -> null
        }
    }
}

data class AuthInputState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

enum class AuthMode {
    SIGN_IN,
    SIGN_UP
}

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object RequiresHealthInfo : AuthState()
    data class Error(val errorMessage: String) : AuthState()
}