package com.lalapanbulaos.nutric.features.home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ActivityCard(
    date: String,
    foodName: String,
    currentCalories: Int,
    targetCalories: Int,
    imageResource:Int
) {
    val progress = currentCalories.toFloat() / targetCalories.toFloat()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.Neutral.color00, shape = RoundedCornerShape(12.dp)),

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 6.5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(painter = painterResource(imageResource), modifier = Modifier.size(60.dp), contentDescription = foodName)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = date,
                        style = NutriCTypography.bodyXs,
                        color = Colors.Neutral.color50

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = foodName,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.widthIn(max = 160.dp)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(80.dp)
            ) {
                CircularProgressBar(
                    current = currentCalories,
                    target = targetCalories,
                    strokeWidth = 8.dp
                )
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "$currentCalories",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Kalori", style = TextStyle(fontSize = 8.sp), color = Colors.Neutral.color50)
                }

            }
        }
    }
}

@Composable
fun CircularProgressBar(
    current: Int,
    target: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val progress = (current.toFloat() / target).coerceIn(0f, 1f) // memastikan progress antara 0-1
    val sweepAngle = progress * 360f

    Canvas(modifier = modifier.size(60.dp)) {
        drawCircle(
            color = Colors.Secondary.color50,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        drawArc(
            color = if (progress >= 1f) Colors.Secondary.color50 else Colors.Primary.color50,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}