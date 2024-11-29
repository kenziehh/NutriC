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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCNeutralButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun StatisticScreen() {
    val selectedTabIndex = remember { mutableStateOf(0) }
    val tabTypeNames = listOf("Hari", "Minggu", "Bulan")

    Column(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Statistik",
                style = NutriCTypography.subHeadingSm,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth().height(60.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(tabTypeNames.size) { index ->
                    if (selectedTabIndex.value == index) {
                        NutriCButton(
                            modifier = Modifier,
                            onClick = {},
                            content = { Text(tabTypeNames[index], style = NutriCTypography.bodySm, color = Colors.Neutral.color00) }
                        )
                    } else {
                        NutriCNeutralButton(
                            modifier = Modifier,
                            content = { Text(tabTypeNames[index], style = NutriCTypography.bodySm, color = Colors.Neutral.color30) },
                            onClick = { selectedTabIndex.value = index }
                        )
                    }
                }
            }
        }
        NutritionTable()
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
    NutritionTable()

}

@Composable
fun NutritionTable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Alomani.green, shape = RoundedCornerShape(12.dp))
    ) {
        TableHeader()
        NutritionRow("Protein", "6gram", "10%")
        NutritionRow("Karbohidrat", "10gram", "60%")
        NutritionRow("Lemak", "5gram", "48%")
        NutritionRow("Gula", "1gram", "67%")
        NutritionRow("Garam", "7gram", "69%")
        NutritionRow("Serat", "-", "10%")
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
        Text("")
        Text(
            text = "Rata-rate",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "%",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
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
            style = NutriCTypography.subHeadingXs
        )
        Text(
            text = amount,
            style = NutriCTypography.subHeadingXs
        )
        Text(
            text = percentage,
            style = NutriCTypography.subHeadingXs
        )
    }
}

