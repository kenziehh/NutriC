package com.lalapanbulaos.nutric.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import com.lalapanbulaos.nutric.features.auth.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val signInUseCase: SignInUseCase, private val signUpUseCase: SignUpUseCase) : ViewModel() {

    // State for managing input fields (username and password)
    private val _inputState = MutableStateFlow(InputState())
    val inputState: StateFlow<InputState> = _inputState

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isSignUpMode = MutableStateFlow(false)
    val isSignUpMode: StateFlow<Boolean> = _isSignUpMode

    fun onUsernameChanged(username: String) {
        _inputState.value = _inputState.value.copy(username = username)
    }

    fun onPasswordChanged(password: String) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _inputState.value = _inputState.value.copy(confirmPassword = confirmPassword)
    }

    fun toggleSignUpMode(isSignUp: Boolean) {
        _isSignUpMode.value = isSignUp
    }

    // Handle submit
    fun submit() {
        if (_inputState.value.username.isEmpty() || _inputState.value.password.isEmpty()) {
            _authState.value = AuthState.Error("Username and password must not be empty")
            return
        }

        if (_isSignUpMode.value && _inputState.value.password != _inputState.value.confirmPassword) {
            _authState.value = AuthState.Error("Passwords do not match")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = if (_isSignUpMode.value) {
                signUpUseCase.execute(_inputState.value.username, _inputState.value.password)
            } else {
                signInUseCase.execute(_inputState.value.username, _inputState.value.password)
            }

            // Handle success or failure based on the result
            result.onSuccess {
                _authState.value = AuthState.Success
                _inputState.value = InputState() // Reset input state
            }.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    // State for managing input fields
    data class InputState(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = ""
    )

    // State sealed class to represent different UI states
    sealed class AuthState {
        object Idle : AuthState() // Initial state when no action has been taken
        object Loading : AuthState() // State while loading
        object Success : AuthState() // State when sign-in or sign-up is successful
        data class Error(val errorMessage: String) : AuthState() // State when there is an error
    }
}
