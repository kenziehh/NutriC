package com.lalapanbulaos.nutric.features.healthinfo.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.component.HealthInfoInputStepContent
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCOutlinedButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography

@Composable
fun HealthInfoScreen(viewModel: HealthInfoViewModel = hiltViewModel()) {
  val stepList = viewModel.stepList
  val currentStep = viewModel.currentStep

  Column(
    modifier = Modifier
      .fillMaxSize()
      .safeDrawingPadding()
      .padding(
        20.dp
      )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        "Langkah ${currentStep.value + 1}",
        style = CustomTypography.labelMedium,
        color = Colors.Primary.color40
      )

      Spacer(modifier = Modifier.width(16.dp))

      LinearProgressIndicator(
        modifier = Modifier
          .height(6.dp)
          .weight(1f),
        progress = { (currentStep.value + 1).toFloat() / stepList.size },
        color = Colors.Primary.color40,
        trackColor = Color.LightGray,
      )
    }

    Spacer(modifier = Modifier.height(50.dp))

    Text(
      stepList[currentStep.value].title,
      style = CustomTypography.headlineMedium,
      color = Colors.Primary.color40
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(stepList[currentStep.value].description, style = CustomTypography.bodyLarge)

    Spacer(modifier = Modifier.height(12.dp))

    stepList[currentStep.value].notes?.let {
      Text(it, color = Colors.Danger.color30, style = CustomTypography.bodyMedium)
    }

    Spacer(modifier = Modifier.height(12.dp))

    Box(
      modifier = Modifier.weight(1f)
    ) {
      HealthInfoInputStepContent(
        step = stepList[currentStep.value],
        viewModel = viewModel
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      if (currentStep.value > 0) {
        NutriCOutlinedButton(
          text = "Kembali",
          modifier = Modifier.weight(1f),
          onClick= {
            if (currentStep.value > 0) {
              currentStep.value--
            }
          }
        )

        Spacer(modifier = Modifier.width(12.dp))
      }

      NutriCButton(
        modifier = Modifier.weight(1f),
        onClick = {
          viewModel.goToNextStep()
        },
        text = if (currentStep.value < stepList.size - 1) "Lanjut" else "Simpan",
      )
    }
  }
}
