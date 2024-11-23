package com.lalapanbulaos.nutric.features.home.presentation.component

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun NutrientProgressBar(label: String, current: Int, goal: Int, imageResource: Int) {
    val adjustedCurrent = when {
        current == 0 -> 1
        current > goal -> maxOf(goal - 1, 0)
        else -> current
    }

    val progress = if (goal > 0) min(adjustedCurrent.toFloat() / goal, 1f) else 0f

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(imageResource),
            modifier = Modifier.size(24.dp),
            contentDescription = label
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, color = Colors.Neutral.color50, style = NutriCTypography.bodyXs)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$current/$goal g",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
                        .weight(progress)
                        .height(8.dp)
                        .background(
                            color = Colors.Secondary.color50,
                            shape = RoundedCornerShape(100.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .weight(1f - progress)
                        .height(8.dp)
                        .background(
                            Colors.Secondary.color20.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(100.dp)
                        )
                )
            }
        }
    }
}
