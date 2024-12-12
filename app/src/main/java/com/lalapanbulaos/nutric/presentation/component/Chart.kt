package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.features.meal.usecase.MacroTargetAverage
import com.lalapanbulaos.nutric.features.meal.usecase.MacroTargetPercentage
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun BarChart(macroTargetAverage: MacroTargetAverage) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        androidx.compose.material3.Text("Nutrisi", style = NutriCTypography.subHeadingMd)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            val nutrients = listOf("Protein", "Karbo", "Lemak", "Gula", "Kalori", "Serat")
            val percentages = listOf(100f, 80f, 60f, 40f, 20f, 10f)
            val listMacroTargetPercentage = listOf(
                macroTargetAverage.averagePercentage.protein,
                macroTargetAverage.averagePercentage.carbohydrates,
                macroTargetAverage.averagePercentage.fat,
                macroTargetAverage.averagePercentage.sugar,
                macroTargetAverage.averagePercentage.calories,
                macroTargetAverage.averagePercentage.fiber
            )

            Column(
                modifier = Modifier
                    .height(150.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                percentages.forEachIndexed { index, percentage ->
                    androidx.compose.material3.Text(
                        text = "${(percentage).toInt()}%",
                        style = NutriCTypography.subHeadingXs,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                var isAnimationStarted by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    isAnimationStarted = true
                }

                nutrients.zip(listMacroTargetPercentage).forEach { (label, percentage) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        val normalizedPercentage = percentage / 100

                        val animatedPercentage by animateFloatAsState(
                            targetValue = if (isAnimationStarted) normalizedPercentage else 0f,
                            animationSpec = tween(
                                durationMillis = 1500,
                                easing = FastOutSlowInEasing
                            ),
                            label = "percentage"
                        )

                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .width(14.dp)
                                .background(Colors.Alomani.green, shape = RoundedCornerShape(8.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(150.dp * animatedPercentage)
                                    .width(14.dp)
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        Colors.Primary.color40,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        androidx.compose.material3.Text(
                            text = label,
                            style = NutriCTypography.bodySm
                        )
                    }
                }
            }

        }
    }

}