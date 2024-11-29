package com.lalapanbulaos.nutric.features.healthinfo.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.core.models.Allergy
import com.lalapanbulaos.nutric.presentation.theme.Colors

@Composable
fun AllergyItem(
  allergy: Allergy,
  onClick: () -> Unit, // Lambda to handle item click
  isSelected: Boolean = false
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1f) // Ensures square-shaped items
      .clickable(onClick = onClick)
      .border(
        width = 1.dp,
        color = if (isSelected) Colors.Primary.color40 else Colors.Neutral.color20,
        shape = RoundedCornerShape(8.dp)
      )
    ,
    shape = RoundedCornerShape(8.dp)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.padding(16.dp)
    ) {
      Text(
        text = allergy.name, // Replace with the allergy's display name
        textAlign = TextAlign.Center
      )
    }
  }
}