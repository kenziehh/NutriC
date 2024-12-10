package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun NutrientProgressBar(
    label: String,
    current: Int,
    goal: Int,
    imageResource: Int,
    currentPlusScannedFood: Int? = null
) {
    val adjustedCurrent = min(current, goal)
    val totalProgress = currentPlusScannedFood?.let { min(current + it, goal) } ?: adjustedCurrent

    val progress = if (goal > 0) adjustedCurrent.toFloat() / goal else 0f
    val additionalProgress = if (goal > 0) totalProgress.toFloat() / goal else 0f

    // Ensure weights are greater than zero
    val validProgress = maxOf(progress, 0.01f)
    val validAdditionalProgress = maxOf(additionalProgress - progress, 0.01f)
    val remainingProgress = maxOf(1f - additionalProgress, 0.01f)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(imageResource),
            modifier = Modifier.size(24.dp),
            contentDescription = label
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                color = Colors.Neutral.color50,
                style = NutriCTypography.bodyXs
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$current/$goal g",
                style = NutriCTypography.subHeadingSm
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(validProgress)
                        .height(4.dp)
                        .background(
                            color = Colors.Secondary.color50,
                            shape = RoundedCornerShape(100.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .weight(validAdditionalProgress)
                        .height(4.dp)
                        .background(
                            color = Colors.Warning.color50,
                            shape = RoundedCornerShape(100.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .weight(remainingProgress)
                        .height(4.dp)
                        .background(
                            Colors.Secondary.color20.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(100.dp)
                        )
                )
            }
        }
    }
}
