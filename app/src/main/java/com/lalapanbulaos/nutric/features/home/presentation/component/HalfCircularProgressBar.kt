package com.lalapanbulaos.nutric.features.home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun HalfCircularProgressBar(totalCalories: Int, calorieNeeds: Int) {
    val progress = if (totalCalories > calorieNeeds) 1f else totalCalories.toFloat() / calorieNeeds
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight().width(200.dp)
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawArc(
                color = Colors.Neutral.color00,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                color = Colors.Primary.color50,
                startAngle = 180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.calories),
                contentDescription = "Calories Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "$totalCalories",
                color = Color.Black,
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Kalori",
                color = Color.Black,
                style = NutriCTypography.subHeadingMd
            )
        }
    }
}