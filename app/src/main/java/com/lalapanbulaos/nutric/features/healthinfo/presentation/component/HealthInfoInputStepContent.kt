package com.lalapanbulaos.nutric.features.healthinfo.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoEvent
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoUiState
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoInputStep
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField

@Composable
fun HealthInfoInputStepContent(
    step: HealthInfoInputStep,
    state: HealthInfoUiState,
    onEvent: (HealthInfoEvent) -> Unit
) {
    when (step.id) {
        "age" -> NutriCTextField(
            value = state.inputState.age,
            label = "Age",
            onValueChange = { onEvent(HealthInfoEvent.OnAgeChanged(it)) },
            keyboardType = KeyboardType.Number
        )
        "height" -> NutriCTextField(
            value = state.inputState.height,
            label = "Height",
            onValueChange = { onEvent(HealthInfoEvent.OnHeightChanged(it)) },
            keyboardType = KeyboardType.Number
        )
        "weight" -> NutriCTextField(
            value = state.inputState.weight,
            label = "Weight",
            onValueChange = { onEvent(HealthInfoEvent.OnWeightChanged(it)) },
            keyboardType = KeyboardType.Number
        )
        "allergies" -> AllergyGrid(
            allergies = state.allergies,
            selectedAllergies = state.inputState.allergies,
            onItemClick = { onEvent(HealthInfoEvent.OnAllergyToggled(it)) }
        )
    }
}