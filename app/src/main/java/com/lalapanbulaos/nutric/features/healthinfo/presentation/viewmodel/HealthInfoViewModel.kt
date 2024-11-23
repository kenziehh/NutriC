package com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.presentation.component.AllergyGrid
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetAllergiesUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetHealthInfoUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.ValidateInputStepUseCase
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HealthInfoViewModel @Inject constructor(
  getAllergiesUseCase: GetAllergiesUseCase,
  getHealthInfoUseCase: GetHealthInfoUseCase,
  private val validateInputStepUseCase: ValidateInputStepUseCase
) :
  ViewModel() {
  val stepList = listOf(
    HealthInfoInputStep(
      id = "age",
      title = "Berapa umur kamu?",
      description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
    ),
    HealthInfoInputStep(
      id = "height",
      title = "Berapa tinggi badan kamu?",
      description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
    ),
    HealthInfoInputStep(
      id = "weight",
      title = "Berapa berat badan kamu?",
      description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
    ),
    HealthInfoInputStep(
      id = "allergies",
      title = "Apakah kamu punya alergi?",
      description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
      notes = "Bisa memilih lebih dari 1",
    )
  )

  val currentStep = mutableIntStateOf(0)

  val allergies = getAllergiesUseCase.execute()

  val healthInfo = getHealthInfoUseCase.execute()

  private val currentStepId = stepList[currentStep.intValue].id

  private val isAllowedNext: Boolean
    get() = validateInputStepUseCase.isAllowedNext(
      currentStepId,
      inputState.value
    )

  private val _inputState = MutableStateFlow(InputState())
  val inputState: StateFlow<InputState> = _inputState

  fun onAgeChanged(age: String) {
    _inputState.value = _inputState.value.copy(age = age)
  }

  fun onHeightChanged(height: String) {
    _inputState.value = _inputState.value.copy(height = height)

  }

  fun onWeightChanged(weight: String) {
    _inputState.value = _inputState.value.copy(weight = weight)

  }

  fun onAllergiesChanged(selectedAllergy: Allergy) {
    val updatedAllergies = _inputState.value.allergies.toMutableList()

    if (updatedAllergies.contains(selectedAllergy.name)) {
      updatedAllergies.remove(selectedAllergy.name)
    } else {
      updatedAllergies.add(selectedAllergy.name)
    }

    _inputState.value = _inputState.value.copy(allergies = updatedAllergies)
  }

  fun goToNextStep() {
    if (isAllowedNext && currentStep.intValue < stepList.size - 1) {
      currentStep.intValue++
    }
  }

  fun submit() {


  }

  data class InputState(
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val allergies: List<String> = emptyList()
  )

  data class HealthInfoInputStep(
    val id: String,
    val title: String,
    val description: String,
    val notes: String? = null,
  )
}