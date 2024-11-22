package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import javax.inject.Inject

class ValidateInputStepUseCase @Inject constructor() {
  fun isAllowedNext(currentStepId: String, inputState: HealthInfoViewModel.InputState): Boolean {
    return when (currentStepId) {
      "age" -> inputState.age.isNotEmpty()
      "height" -> inputState.height.isNotEmpty()
      "weight" -> inputState.weight.isNotEmpty()
      "allergies" -> inputState.allergies.isNotEmpty()
      else -> false
    }
  }
}