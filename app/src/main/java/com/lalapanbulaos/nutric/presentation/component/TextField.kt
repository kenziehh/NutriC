package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.presentation.theme.Colors

@Composable
fun NutriCTextField(
    readOnly: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    placeholderColor: Color = Color.Black,
    focusedLabelColor: Color = Colors.Primary.color40,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    // Determine the visual transformation (password or normal)
    val visualTransformation = if (isPassword && !isPasswordVisible.value) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    // The icon to show/hide the password
    val eyeIcon =
        if (isPasswordVisible.value) painterResource(id = R.drawable.ic_eyeoff) else painterResource(
            id = R.drawable.ic_eye
        )


    TextField(
        singleLine = true,
        readOnly = readOnly,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Colors.Neutral.color30) },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Colors.Neutral.color30, // Border color
                shape = RoundedCornerShape(14.dp) // Rounded corners
            ),
        visualTransformation = visualTransformation, // Apply password transformation if required
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = textColor,
            placeholderColor = placeholderColor,
            focusedLabelColor = focusedLabelColor
        ),
        trailingIcon = {
            // Eye icon to toggle password visibility
            if (isPassword) {
                IconButton(onClick = {
                    // Toggle password visibility
                    isPasswordVisible.value = !isPasswordVisible.value
                }) {
                    Icon(
                        painter = eyeIcon, contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}