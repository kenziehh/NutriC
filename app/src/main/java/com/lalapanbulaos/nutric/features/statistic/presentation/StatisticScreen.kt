package com.lalapanbulaos.nutric.features.statistic.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.core.utils.formatIsoDate
import com.lalapanbulaos.nutric.features.home.presentation.component.ActivityCard
import com.lalapanbulaos.nutric.features.meal.usecase.MacroTargetAverage
import com.lalapanbulaos.nutric.presentation.component.BarChart
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCNeutralButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun StatisticScreen(viewModel: StatisticViewModel = hiltViewModel()) {
    val selectedTabIndex = viewModel.selectedTabIndex.value
    val totalMacros = viewModel.uiState.collectAsState().value.totalMacros
    val meals = viewModel.mealList.collectAsState().value
    val averageMacroTarget = viewModel.averageMacroTargetPercentage.collectAsState().value

    val tabTypeNames = listOf("Hari", "Minggu", "Bulan")

    Column(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Statistik",
                style = NutriCTypography.subHeadingSm,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Custom Tab dengan NutriCButton dan NutriCNeutralButton
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tabTypeNames.forEachIndexed { index, name ->
                    if (selectedTabIndex == index) {
                        NutriCButton(
                            onClick = { viewModel.onTabSelected(index) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            content = {
                                Text(text = name, color = Color.White, textAlign = TextAlign.Center)
                            }
                        )
                    } else {
                        NutriCNeutralButton(
                            onClick = { viewModel.onTabSelected(index) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            content = {
                                Text(text = name, color = Color.Black, textAlign = TextAlign.Center)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BarChart(averageMacroTarget)

            Spacer(modifier = Modifier.height(16.dp))

            totalMacros?.let {
                NutritionTable(averageMacroTarget)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                if (meals.isEmpty()) {
                        Text(
                            text = "Belum ada aktivitas hari ini",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            color = Colors.Neutral.color40,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )

                } else {
                    for (meal in meals) {
                        meal.food.foodMacroNutrient?.calories?.let { calories ->
                            ActivityCard(
                                currentCalories = calories.toInt(),
                                date = formatIsoDate(meal.created_at),
                                foodName = meal.food.name,
                                imageResource = R.drawable.activity1,
                                targetCalories = 2400
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionTable(averageMacroTarget: MacroTargetAverage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Alomani.green, shape = RoundedCornerShape(12.dp))
    ) {
        TableHeader()
        NutritionRow("Protein", averageMacroTarget.averageValues.protein.toString(), "${averageMacroTarget.averagePercentage.protein.toInt()}%")
        NutritionRow("Karbohidrat", averageMacroTarget.averageValues.carbohydrates.toString(), "${averageMacroTarget.averagePercentage.carbohydrates.toInt()}%")
        NutritionRow("Lemak", averageMacroTarget.averageValues.fat.toString(), "${averageMacroTarget.averagePercentage.fat.toInt()}%")
        NutritionRow("Gula", averageMacroTarget.averageValues.sugar.toString(), "${averageMacroTarget.averagePercentage.sugar.toInt()}%")
        NutritionRow("Kalori", averageMacroTarget.averageValues.calories.toString(), "${averageMacroTarget.averagePercentage.calories.toInt()}%")
        NutritionRow("Serat", averageMacroTarget.averageValues.fiber.toString(), "${averageMacroTarget.averagePercentage.fiber.toInt()}%")
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Rata-rata",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Jumlah",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        Text(
            text = "%",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun NutritionRow(
    nutrient: String,
    amount: String,
    percentage: String,
    backgroundColor: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = nutrient,
            style = NutriCTypography.subHeadingXs,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = amount,
            style = NutriCTypography.subHeadingXs,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )
        Text(
            text = percentage,
            style = NutriCTypography.subHeadingXs,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

