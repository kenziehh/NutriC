package com.lalapanbulaos.nutric.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.FoodMacroNutrient
import com.lalapanbulaos.nutric.features.meal.data.models.MealResponse
import com.lalapanbulaos.nutric.features.meal.usecase.GetMealUseCase
import com.lalapanbulaos.nutric.features.meal.usecase.GetTotalMacroNutrientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMealUseCase: GetMealUseCase,
    private val getTotalMacroNutrientUseCase: GetTotalMacroNutrientUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _userName = MutableStateFlow("Guest")
    val userName: StateFlow<String> = _userName.asStateFlow()

    init {
        fetchMeals()
        fetchUserName()
    }

    fun fetchMeals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val response = getMealUseCase.execute()
            Log.d("HomeViewModel", "Raw response: ${response}")

            response.onSuccess { meals ->
                Log.d("HomeViewModel", "Meals fetched successfully: $meals")
                _uiState.update { currentState ->
                    currentState.copy(
                        meals = meals,
                        isLoading = false,
                        error = null
                    )
                }

                // Fetch total macro nutrients after meals are fetched
                fetchTotalMacros(meals)
            }.onFailure { throwable ->
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

    private fun fetchTotalMacros(meals: List<MealResponse>) {
        viewModelScope.launch {
            val result = getTotalMacroNutrientUseCase.execute(meals)
            result.onSuccess { totalMacros ->
                Log.d("HomeViewModel", "Total macros calculated: $totalMacros")
                _uiState.update { currentState ->
                    currentState.copy(
                        totalMacros = totalMacros
                    )
                }
            }.onFailure { throwable ->
                Log.e("HomeViewModel", "Error calculating total macros: ", throwable)
            }
        }
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { user ->
                _userName.value = user?.username ?: "Guest"
            }
        }
    }
}

data class HomeUiState(
    val meals: List<MealResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: MealError? = null,
    val totalMacros: FoodMacroNutrient? = null // Added to store total macronutrients
)

sealed class MealError {
    data class MealFetchError(val throwable: Throwable) : MealError()
}