package com.lalapanbulaos.nutric.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    // State for managing input fields (username and password)
    private val _inputState = MutableStateFlow(InputState())
    val inputState: StateFlow<InputState> = _inputState

    // State for managing sign-in process (loading, success, error)
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    // Handle username input changes
    fun onUsernameChanged(username: String) {
        _inputState.value = _inputState.value.copy(username = username)
    }

    // Handle password input changes
    fun onPasswordChanged(password: String) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    // Handle sign-in logic
    fun signIn() {
        // Validate input fields
        if (_inputState.value.username.isEmpty() || _inputState.value.password.isEmpty()) {
            _signInState.value = SignInState.Error("Username and password must not be empty")
            return
        }

        // Set the state to Loading before the operation starts
        _signInState.value = SignInState.Loading

        // Perform the sign-in operation using the UseCase
        viewModelScope.launch {
            try {
                val result = signInUseCase.execute(_inputState.value.username, _inputState.value.password)

                // Set the state to Success with the result
                _signInState.value = SignInState.Success(result.isSuccess)
            } catch (e: Exception) {
                // Set the state to Error if an exception occurs
                _signInState.value = SignInState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // State for managing input fields
    data class InputState(
        val username: String = "",
        val password: String = ""
    )

    // State sealed class to represent different UI states
    sealed class SignInState {
        data object Idle : SignInState() // Initial state when no action has been taken
        data object Loading : SignInState() // State while loading
        data class Success(val result: Boolean) : SignInState() // State when sign-in is successful
        data class Error(val errorMessage: String) : SignInState() // State when there is an error
    }
}
