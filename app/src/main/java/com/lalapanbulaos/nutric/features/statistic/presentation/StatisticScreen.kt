package com.lalapanbulaos.nutric.features.statistic.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lalapanbulaos.nutric.core.models.FoodMacroNutrient
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCNeutralButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun StatisticScreen(viewModel: StatisticViewModel = hiltViewModel()) {
    val selectedTabIndex = viewModel.selectedTabIndex.value
    val totalMacros = viewModel.uiState.collectAsState().value.totalMacros

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

            totalMacros?.let {
                NutritionTable(foodMacroNutrient = totalMacros)
            }
        }
    }
}






@Composable
fun BarChart(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Nutrisi", style = NutriCTypography.subHeadingMd)

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            val nutrients = listOf("Protein", "Karbo", "Lemak", "Gula", "Garam", "Serat")
            val percentages = listOf(0.8f, 0.6f, 0.48f, 0.67f, 0.69f, 0.69f)

            nutrients.zip(percentages).forEach { (label, percentage) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .height(100.dp * percentage)
                            .width(16.dp)
                            .background(Colors.Primary.color40, shape = RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(label, style = NutriCTypography.bodyXxs)
                }
            }
        }
    }

}

@Composable
fun NutritionTable(foodMacroNutrient: FoodMacroNutrient) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Alomani.green, shape = RoundedCornerShape(12.dp))
    ) {
        TableHeader()
        NutritionRow("Protein", foodMacroNutrient.protein.toString(), "10%")
        NutritionRow("Karbohidrat", foodMacroNutrient.carbohydrates.toString(), "60%")
        NutritionRow("Lemak", foodMacroNutrient.fat.toString(), "48%")
        NutritionRow("Gula", foodMacroNutrient.sugar.toString(), "67%")
        NutritionRow("Serat", foodMacroNutrient.fiber.toString(), "10%")
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

