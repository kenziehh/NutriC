package com.lalapanbulaos.nutric.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import com.lalapanbulaos.nutric.features.meal.usecase.GetMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMealUseCase: GetMealUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _userName = MutableStateFlow("Guest")
    val userName: StateFlow<String> = _userName.asStateFlow()

    init {
        fetchMeals()
        fetchUserName() // Assume this method exists to fetch the user's name
    }

    fun fetchMeals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val response = getMealUseCase.execute()
            Log.d("HomeViewModel", "Raw response: ${response}")

            response.onSuccess { meals ->
                // Log the successful response
                Log.d("HomeViewModel", "Meals fetched successfully: $meals")

                _uiState.update { currentState ->
                    currentState.copy(
                        meals = meals,
                        isLoading = false,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                // Log the failure response
                Log.e("HomeViewModel", "Error fetching meals: ", throwable)

                _uiState.update { currentState ->
                    currentState.copy(
                        error = MealError.MealFetchError(throwable),
                        isLoading = false
                    )
                }
            }
        }

    }


    private fun fetchUserName() {
        // Implement user name fetching logic here
        // For now, we'll just set a dummy name
        _userName.value = "John Doe"
    }
}

data class HomeUiState(
    val meals: List<MealResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: MealError? = null
)

sealed class MealError {
    data class MealFetchError(val throwable: Throwable) : MealError()
}
