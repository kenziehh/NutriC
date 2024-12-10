package com.lalapanbulaos.nutric.features.statistic.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class StatisticViewModel @Inject constructor(
    private val getMealUseCase: GetMealUseCase,
    private val getTotalMacroNutrientUseCase: GetTotalMacroNutrientUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {
    private val _mealList = mutableStateOf<List<MealResponse>>(emptyList())
    val mealList: State<List<MealResponse>> = _mealList

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
        fetchMealsForSelectedTab()
    }

    init {
        fetchMealsForSelectedTab()
    }

    private fun fetchMealsForSelectedTab() {
        val filterBy = when (_selectedTabIndex.value) {
            0 -> "daily"    // Filter for daily data
            1 -> "weekly"   // Filter for weekly data
            2 -> "monthly"  // Filter for monthly data
            else -> "daily" // Default to daily
        }

        viewModelScope.launch {
            val result = getMealUseCase.execute(filterBy)
            result.onSuccess { meals ->
                Log.e("StatisticViewModel", meals.toString())
                _mealList.value = meals
                fetchTotalMacros(meals = meals)
            }
            result.onFailure { exception ->
                Log.e("StatisticViewModel", "Error fetching meals", exception)
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