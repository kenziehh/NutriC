package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.InputState
import javax.inject.Inject

class ValidateInputStepUseCase @Inject constructor() {
  fun isAllowedNext(currentStepId: String, inputState: InputState): Boolean {
    return when (currentStepId) {
      "gender" -> inputState.gender.isNotEmpty()
      "age" -> inputState.age.isNotEmpty()
      "height" -> inputState.height.isNotEmpty()
      "weight" -> inputState.weight.isNotEmpty()
      "activity_level" -> true
      "allergies" -> true
      else -> false
    }
  }
}