package com.lalapanbulaos.nutric.features.healthinfo.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy

@Composable
fun AllergyGrid(
  allergies: List<Allergy>,
  selectedAllergies: List<String>,
  onItemClick: (Allergy) -> Unit,
) {
  LazyVerticalGrid(
    modifier = Modifier,
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(allergies) { allergy ->
      AllergyItem(allergy = allergy, onClick = { onItemClick(allergy) }, isSelected = selectedAllergies.contains(allergy.name))
    }
  }
}