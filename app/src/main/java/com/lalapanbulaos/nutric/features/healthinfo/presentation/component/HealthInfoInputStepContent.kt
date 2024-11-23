package com.lalapanbulaos.nutric.features.healthinfo.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField

@Composable
fun HealthInfoInputStepContent(step: HealthInfoViewModel.HealthInfoInputStep, viewModel: HealthInfoViewModel) {
  when (step.id) {
    "age" -> NutriCTextField(
      value = viewModel.inputState.collectAsState().value.age,
      onValueChange = { viewModel.onAgeChanged(it) },
      label = "Masukkan umur kamu",
      placeholder = "ex: 20 (tahun)",
      keyboardType = KeyboardType.Number
    )
    "height" -> NutriCTextField(
      value = viewModel.inputState.collectAsState().value.height,
      onValueChange = { viewModel.onHeightChanged(it) },
      label = "Masukkan tinggi badan kamu",
      placeholder = "ex: 160 (cm)",
      keyboardType = KeyboardType.Number
    )
    "weight" -> NutriCTextField(
      value = viewModel.inputState.collectAsState().value.weight,
      onValueChange = { viewModel.onWeightChanged(it) },
      label = "Masukkan berat badan kamu",
      placeholder = "ex: 60 (kg)",
      keyboardType = KeyboardType.Number
    )
    "allergies" -> AllergyGrid(
      allergies = viewModel.allergies.collectAsState(emptyList()).value,
      selectedAllergies = viewModel.inputState.collectAsState().value.allergies,
      onItemClick = { viewModel.onAllergiesChanged(it) }
    )
  }
}