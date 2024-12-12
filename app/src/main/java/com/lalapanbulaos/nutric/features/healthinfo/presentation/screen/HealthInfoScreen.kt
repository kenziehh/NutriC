package com.lalapanbulaos.nutric.features.healthinfo.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.features.healthinfo.presentation.component.HealthInfoInputStepContent
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoEvent
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoUiState
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoInputStep
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCOutlinedButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography

@Composable
fun HealthInfoScreen(
  onHealthInfoAvailable: () -> Unit,
  viewModel: HealthInfoViewModel = hiltViewModel(),
) {
  val uiState = viewModel.uiState.collectAsState().value
  val steps = viewModel.steps

  LaunchedEffect(uiState.healthInfo) {
    if (uiState.healthInfo != null) {
      onHealthInfoAvailable()
    }
  }

  HealthInfoContent(
    state = uiState,
    steps = steps,
    onEvent = viewModel::onEvent,
  )
}

@Composable
private fun HealthInfoContent(
  state: HealthInfoUiState,
  steps: List<HealthInfoInputStep>,
  onEvent: (HealthInfoEvent) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .safeDrawingPadding()
      .padding(vertical = 20.dp, horizontal = 16.dp)
  ) {
    // Progress Header
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        "Langkah ${state.currentStep + 1}",
        style = CustomTypography.labelMedium,
        color = Colors.Primary.color40
      )

      Spacer(modifier = Modifier.width(16.dp))

      LinearProgressIndicator(
        modifier = Modifier
          .height(6.dp)
          .weight(1f),
        progress = { (state.currentStep + 1).toFloat() / steps.size },
        color = Colors.Primary.color40,
        trackColor = Color.LightGray,
      )
    }

    Spacer(modifier = Modifier.height(50.dp))

    // Current Step Information
    val currentStep = steps[state.currentStep]

    Text(
      currentStep.title,
      style = CustomTypography.headlineMedium,
      color = Colors.Primary.color40
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      currentStep.description,
      style = CustomTypography.bodyLarge
    )

    Spacer(modifier = Modifier.height(12.dp))

    currentStep.notes?.let {
      Text(
        it,
        color = Colors.Danger.color30,
        style = CustomTypography.bodyMedium
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Input Content
    Box(modifier = Modifier.weight(1f)) {
      HealthInfoInputStepContent(
        step = currentStep,
        state = state,
        onEvent = onEvent
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Navigation Buttons
    Row(modifier = Modifier.fillMaxWidth()) {
      if (state.currentStep > 0) {
        NutriCOutlinedButton(
          text = "Kembali",
          modifier = Modifier.weight(1f),
          onClick = { onEvent(HealthInfoEvent.OnPreviousStep) }
        )

        Spacer(modifier = Modifier.width(12.dp))
      }

      NutriCButton(
        modifier = Modifier.weight(1f),
        onClick = {
          if (state.currentStep < steps.size - 1) {
            onEvent(HealthInfoEvent.OnNextStep)
          } else {
            onEvent(HealthInfoEvent.OnSubmit)
          }
        },
        text = if (state.currentStep < steps.size - 1) "Lanjut" else "Simpan",
        enabled = state.isAllowedNext
      )
    }
  }
}