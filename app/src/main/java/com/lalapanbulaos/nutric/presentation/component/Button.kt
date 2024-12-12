package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography

// Base configuration for button styles
private val DefaultButtonShape = RoundedCornerShape(14.dp)
private val DefaultButtonPadding = PaddingValues(16.dp)

@Composable
fun NutriCButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: (@Composable (() -> Unit))? = null,
    text: String = "NutriC"
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.Primary.color40,
            contentColor = Color.White,
            disabledBackgroundColor = Colors.Primary.color20,
            disabledContentColor = Color.White
        ),
        shape = DefaultButtonShape,
        contentPadding = DefaultButtonPadding,
        enabled = enabled
    ) {
        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                style = CustomTypography.labelLarge
            )
        }
    }
}

@Composable
fun NutriCOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = DefaultButtonShape,
        contentPadding = DefaultButtonPadding,
        enabled = enabled,
        border = BorderStroke(1.dp, Colors.Primary.color40),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Colors.Primary.color40,
            disabledContentColor = Colors.Primary.color30,
        )
    ) {
        Text(text = text, style = CustomTypography.labelLarge)
    }
}

@Composable
fun NutriCNeutralButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: (@Composable (() -> Unit))? = null,
    text: String = "NutriC"
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.Neutral.color20,
            contentColor = Colors.Neutral.color50,
            disabledBackgroundColor = Colors.Neutral.color10,
            disabledContentColor = Colors.Neutral.color50
        ),
        shape = DefaultButtonShape,
        contentPadding = DefaultButtonPadding,
        enabled = enabled
    ) {
        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                style = CustomTypography.labelLarge
            )
        }
    }
}

