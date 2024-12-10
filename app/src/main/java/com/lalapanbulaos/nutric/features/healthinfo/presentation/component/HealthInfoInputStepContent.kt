package com.lalapanbulaos.nutric.features.healthinfo.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.features.healthinfo.data.model.ActivityLevel
import com.lalapanbulaos.nutric.features.healthinfo.data.model.describeActivityLevel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoEvent
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoUiState
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoInputStep
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTheme
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography
import java.util.Locale

@Composable
fun HealthInfoInputStepContent(
    step: HealthInfoInputStep,
    state: HealthInfoUiState,
    onEvent: (HealthInfoEvent) -> Unit
) {
    when (step.id) {
        "gender" -> {
            var expanded by remember { mutableStateOf(false) }
            val genders = listOf("Male", "Female") // Options
            val selectedGender = state.inputState.gender // Current value

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            color = Colors.Neutral.color30,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = if (selectedGender.isEmpty()) "Select Gender" else selectedGender.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = NutriCTypography.bodyLg,
                        color = Colors.Neutral.color30,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genders.forEach { gender ->
                        DropdownMenuItem(
                            onClick = {
                                onEvent(HealthInfoEvent.OnGenderChanged(gender.lowercase())) // Handle selection
                                expanded = false
                            },
                            text = { Text(text = gender) },
                        )
                    }
                }
            }

        }

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

        "activity_level" -> {
            var expanded by remember { mutableStateOf(false) }
            val activityLevels = ActivityLevel.entries.toTypedArray() // Options
            val selectedActivityLevel = state.inputState.activityLevel // Current value

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            color = Colors.Neutral.color30,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = describeActivityLevel(selectedActivityLevel),
                        modifier = Modifier.fillMaxWidth(),
                        style = NutriCTypography.bodyLg,
                        color = Colors.Neutral.color30,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    activityLevels.forEach { level ->
                        DropdownMenuItem(
                            onClick = {
                                onEvent(HealthInfoEvent.OnActivityLevelChanged(level))
                                expanded = false
                            },
                            text = { Text(text = describeActivityLevel(level)) },
                        )
                    }
                }
            }
        }

        "allergies" -> AllergyGrid(
            allergies = state.allergies,
            selectedAllergies = state.inputState.allergies,
            onItemClick = { onEvent(HealthInfoEvent.OnAllergyToggled(it)) }
        )
    }
}