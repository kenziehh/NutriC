package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.presentation.theme.Colors

@Composable
fun NutriCButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.Primary.color40,
            contentColor = Color.White,
            disabledBackgroundColor = Colors.Primary.color30,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled
    ) {
       content()
    }
}