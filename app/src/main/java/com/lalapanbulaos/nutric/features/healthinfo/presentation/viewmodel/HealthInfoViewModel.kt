package com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.features.healthinfo.data.model.ActivityLevel
import com.lalapanbulaos.nutric.core.models.Allergy
import com.lalapanbulaos.nutric.core.models.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfoRequest
import com.lalapanbulaos.nutric.features.healthinfo.usecase.CreateHealthInfoUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetAllergiesUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoInputStep
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoStepManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthInfoViewModel @Inject constructor(
    private val getAllergiesUseCase: GetAllergiesUseCase,
    private val createHealthInfoUseCase: CreateHealthInfoUseCase,
    private val stepManager: HealthInfoStepManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HealthInfoUiState())
    val uiState: StateFlow<HealthInfoUiState> = _uiState.asStateFlow()

    init {
        fetchAllergies()
    }

    fun fetchAllergies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val response = getAllergiesUseCase.execute()

            response.onSuccess {
                _uiState.update { currentState ->
                    currentState.copy(
                        allergies = it,
                        isLoading = false
                    )
                }
            }.onFailure {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = HealthInfoError.AllergiesFetchError(it),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: HealthInfoEvent) {
        when (event) {
            is HealthInfoEvent.OnAgeChanged -> updateAge(event.age)
            is HealthInfoEvent.OnHeightChanged -> updateHeight(event.height)
            is HealthInfoEvent.OnWeightChanged -> updateWeight(event.weight)
            is HealthInfoEvent.OnAllergyToggled -> toggleAllergy(event.allergy)
            HealthInfoEvent.OnNextStep -> goToNextStep()
            HealthInfoEvent.OnPreviousStep -> goToPreviousStep()
            HealthInfoEvent.OnSubmit -> submit()
        }
    }

    private fun updateAge(age: String) {
        _uiState.update { currentState ->
            val newInputState = currentState.inputState.copy(age = age)
            currentState.copy(
                inputState = newInputState,
                isAllowedNext = stepManager.canMoveToNextStep(
                    currentState.currentStep,
                    newInputState
                )
            )
        }
    }

    private fun updateHeight(height: String) {
        _uiState.update { currentState ->
            val newInputState = currentState.inputState.copy(height = height)
            currentState.copy(
                inputState = newInputState,
                isAllowedNext = stepManager.canMoveToNextStep(
                    currentState.currentStep,
                    newInputState
                )
            )
        }
    }

    private fun updateWeight(weight: String) {
        _uiState.update { currentState ->
            val newInputState = currentState.inputState.copy(weight = weight)
            currentState.copy(
                inputState = newInputState,
                isAllowedNext = stepManager.canMoveToNextStep(
                    currentState.currentStep,
                    newInputState
                )
            )
        }
    }

    private fun toggleAllergy(allergy: Allergy) {
        _uiState.update { currentState ->
            val currentAllergies = currentState.inputState.allergies.toMutableList()

            if (currentAllergies.contains(allergy.name)) {
                currentAllergies.remove(allergy.name)
            } else {
                currentAllergies.add(allergy.name)
            }

            val newInputState = currentState.inputState.copy(allergies = currentAllergies)
            currentState.copy(
                inputState = newInputState,
                isAllowedNext = stepManager.canMoveToNextStep(
                    currentState.currentStep,
                    newInputState
                )
            )
        }
    }

    private fun goToNextStep() {
        _uiState.update { currentState ->
            if (currentState.isAllowedNext && !stepManager.isLastStep(currentState.currentStep)) {
                currentState.copy(
                    currentStep = currentState.currentStep + 1,
                    isAllowedNext = stepManager.canMoveToNextStep(
                        currentState.currentStep + 1,
                        currentState.inputState
                    )
                )
            } else currentState
        }
    }

    private fun goToPreviousStep() {
        _uiState.update { currentState ->
            if (!stepManager.isFirstStep(currentState.currentStep)) {
                currentState.copy(
                    currentStep = currentState.currentStep - 1,
                    isAllowedNext = stepManager.canMoveToNextStep(
                        currentState.currentStep - 1,
                        currentState.inputState
                    )
                )
            } else currentState
        }
    }

    private fun submit() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isSubmitting = true) }

                val createRequest = HealthInfoRequest(
                    age = uiState.value.inputState.age.toInt(),
                    height = uiState.value.inputState.height.toDouble(),
                    weight = uiState.value.inputState.weight.toDouble(),
                    allergiesName = uiState.value.inputState.allergies,
                    activityLevel = ActivityLevel.MODERATE
                )

                val response = createHealthInfoUseCase.execute(createRequest)

                if (response.isSuccess) {
                    Log.d("HealthInfoViewModel", "Health info created successfully")
                    _uiState.update { currentState ->
                        currentState.copy(
                            healthInfo = response.getOrNull(),
                            isSubmitting = false
                        )
                    }
                } else {
                    Log.e("HealthInfoViewModel", "Failed to create health info")
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = HealthInfoError.HealthInfoFetchError(
                                Exception("Failed to create health info")
                            ),
                            isSubmitting = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HealthInfoViewModel", "Error submitting health info", e)
                _uiState.update { currentState ->
                    currentState.copy(
                        error = HealthInfoError.HealthInfoFetchError(e),
                        isSubmitting = false
                    )
                }
            }
        }
    }

    val steps: List<HealthInfoInputStep> = stepManager.getSteps()
}

data class HealthInfoUiState(
    val currentStep: Int = 0,
    val inputState: InputState = InputState(),
    val allergies: List<Allergy> = emptyList(),
    val healthInfo: HealthInfo? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: HealthInfoError? = null,
    val isAllowedNext: Boolean = false
)

data class InputState(
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val allergies: List<String> = emptyList()
)

sealed class HealthInfoError {
    data class AllergiesFetchError(val throwable: Throwable) : HealthInfoError()
    data class HealthInfoFetchError(val throwable: Throwable) : HealthInfoError()
}

sealed class HealthInfoEvent {
    data class OnAgeChanged(val age: String) : HealthInfoEvent()
    data class OnHeightChanged(val height: String) : HealthInfoEvent()
    data class OnWeightChanged(val weight: String) : HealthInfoEvent()
    data class OnAllergyToggled(val allergy: Allergy) : HealthInfoEvent()
    object OnNextStep : HealthInfoEvent()
    object OnPreviousStep : HealthInfoEvent()
    object OnSubmit : HealthInfoEvent()
}