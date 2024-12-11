package com.lalapanbulaos.nutric.features.scan_food.presentation.viewmodel

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.core.models.DailyTarget
import com.lalapanbulaos.nutric.core.models.Food
import com.lalapanbulaos.nutric.core.models.FoodMacroNutrient
import com.lalapanbulaos.nutric.features.scan_food.usecase.CreateMealLogUseCase
import com.lalapanbulaos.nutric.features.scan_food.usecase.GetDailyTotalMacros
import com.lalapanbulaos.nutric.features.scan_food.usecase.GetUserDailyNutritionTarget
import com.lalapanbulaos.nutric.features.scan_food.usecase.PredictFoodNameUseCase
import com.lalapanbulaos.nutric.features.scan_food.usecase.PredictNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val predictFoodNameUseCase: PredictFoodNameUseCase,
    private val predictNutritionUseCase: PredictNutritionUseCase,
    private val getUserDailyNutritionTarget: GetUserDailyNutritionTarget,
    private val createMealLogUseCase: CreateMealLogUseCase,
    private val getDailyTotalMacros: GetDailyTotalMacros
) : ViewModel()  {

    val _uiState = MutableStateFlow(ScannerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = ScannerUiState()
    }

    fun onAddMealClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isMealSubmitting = true
            )
            Log.d("ScannerViewModel", "onAddMealClicked called")
            createMealLogUseCase.execute(uiState.value.foodInfo?.id ?: "").onSuccess {
                _uiState.value = _uiState.value.copy(
                    isMealSubmitting = false,
                    isMealSubmitted = true,
                    isScanSuccess = false,
                    isPredictSuccess = false,
                    foodName = null,
                    foodInfo = null,
                    error = null
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isMealSubmitting = false,
                    error = "Error occurred: ${it.message}"
                )
            }
        }
    }

    fun onToggleCameraLensClicked() {
        _uiState.value = _uiState.value.copy(
            lensFacing = if (_uiState.value.lensFacing == CameraSelector.LENS_FACING_BACK) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
        )
    }

    fun onCaptureClicked(image: File) {
        Log.d("ScannerViewModel", "onCaptureClicked called")
            _uiState.value = _uiState.value.copy(isScanning = true, isScanSuccess = false)

            viewModelScope.launch {
                Log.d("ScannerViewModel", "Starting scanning operation")
                try {
                    predictFoodNameUseCase.execute(image).onSuccess {
                        _uiState.value = _uiState.value.copy(
                            isPredictSuccess = false,
                            isScanning = false,
                            isScanSuccess = true,
                            foodName = it.foodName
                        )
                    }.onFailure {
                        _uiState.value = _uiState.value.copy(
                            isScanning = false,
                            error = "Error occurred: ${it.message}"
                        )
                    }

                    Log.d("ScannerViewModel", "Scanning operation completed")

                    Log.d("ScannerViewModel", "Food name: ${_uiState.value.foodName}")
                    Log.d("ScannerViewModel", "isScanSuccess: ${_uiState.value.isScanSuccess}")

                    if (_uiState.value.foodName != null) {
                        _uiState.value = _uiState.value.copy(isPredicting = true)
                        Log.d("ScannerViewModel", "Getting daily target")
                        getUserDailyNutritionTarget.execute().onSuccess {
                            _uiState.value = _uiState.value.copy(
                                dailyTarget = it
                            )
                        }.onFailure {
                            _uiState.value = _uiState.value.copy(
                                error = "Error occurred: ${it.message}"
                            )
                        }

                        getDailyTotalMacros.execute().onSuccess {
                            _uiState.value = _uiState.value.copy(
                                dailyTotalMacros = it
                            )
                        }.onFailure {
                            _uiState.value = _uiState.value.copy(
                                error = "Error occurred: ${it.message}"
                            )
                        }

                        Log.d("ScannerViewModel", "Getting nutrition prediction")
                        predictNutritionUseCase.execute(_uiState.value.foodName!!).onSuccess { foodInfo ->
                            _uiState.value = _uiState.value.copy(
                                isPredicting = false,
                                isPredictSuccess = true,
                                foodInfo = foodInfo,
                                hasAllergy = foodInfo.allergens.isNotEmpty()
                            )
                            Log.d("ScannerViewModel", "Nutrition prediction completed")
                            Log.d("ScannerViewModel", "Food info: ${_uiState.value.foodInfo}")
                        }.onFailure {
                            _uiState.value = _uiState.value.copy(
                                isPredicting = false,
                                error = "Error occurred: ${it.message}"
                            )
                            Log.d("ScannerViewModel", "Error occurred: ${it.message}")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isScanning = false,
                        isPredicting = false,
                        error = "Error occurred: ${e.message}"
                    )
                    Log.d("ScannerViewModel", "Error occurred: ${e.message}")
                }
            }
    }
}

data class CameraUiState(
    val isPermissionGranted: Boolean = false
)

data class ScannerUiState(
    // Camera states
    val isPermissionGranted: Boolean = false,
    val lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    // User states
    val dailyTotalMacros: FoodMacroNutrient? = null,
    val dailyTarget: DailyTarget? = null,
    // Scanning states
    val isScanning: Boolean = false,
    val isScanSuccess: Boolean = false,
    // Prediction states
    val isPredicting: Boolean = false,
    val isPredictSuccess: Boolean = false,
    val imageBuffer: String? = null,
    val foodName: String? = null,
    val hasAllergy: Boolean = false,
    val foodInfo: Food? = null,
    val error: String? = null,
    // Meal submit state
    val isMealSubmitting: Boolean = false,
    val isMealSubmitted: Boolean = false
)