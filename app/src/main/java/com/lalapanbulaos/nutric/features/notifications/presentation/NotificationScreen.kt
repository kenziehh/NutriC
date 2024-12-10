package com.lalapanbulaos.nutric.features.notifications.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography


@Composable
fun NotificationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Colors.Neutral.color00,
                modifier = Modifier.safeDrawingPadding(),
                title = { Text("Notifikasi") },
                navigationIcon = {
                    IconButton(onClick = { /* Back action */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier.padding(padding)
        ) {
            item {
                SectionTitle("Today")
                Spacer(modifier = Modifier.height(8.dp))
                NotificationItem(
                    title = "Ingat untuk minum air!",
                    time = "12:56 PM",
                    description = "Anda belum mencapai target 8 gelas air hari ini.",
                    isCritical = false
                )
                NotificationItem(
                    title = "Terlalu banyak gula dalam konsumsi hari ini!",
                    time = "10:00 AM",
                    description = "Asupan gula Anda telah melebihi 50g (batas harian yang direkomendasikan).",
                    isCritical = true
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SectionTitle("Yesterday")
                Spacer(modifier = Modifier.height(8.dp))
                NotificationItem(
                    title = "Konsumsi lemak stabil dan sehat.",
                    time = "08:00 PM",
                    description = "Asupan lemak harian berada di batas optimal (70g).",
                    isCritical = false
                )
                NotificationItem(
                    title = "Waspada, asupan kalori Anda tinggi!",
                    time = "06:00 PM",
                    description = "Konsumsi kalori mencapai 2800 kcal (target: 2000 kcal).",
                    isCritical = true
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SectionTitle("25 October")
                Spacer(modifier = Modifier.height(8.dp))
                NotificationItem(
                    title = "Kebutuhan protein terpenuhi!",
                    time = "08:00 PM",
                    description = "Konsumsi protein Anda mencapai target harian (60g).",
                    isCritical = false
                )
                NotificationItem(
                    title = "Kurang konsumsi serat kemarin.",
                    time = "10:00 AM",
                    description = "Anda hanya mengonsumsi 10g serat (optimal: 25–30g).",
                    isCritical = true
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        style = NutriCTypography.headingMd,
    )
}

@Composable
fun NotificationItem(title: String, time: String, description: String, isCritical: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.Top),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = if (isCritical) Colors.Danger.color40 else Colors.Success.color40
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = NutriCTypography.bodyMd, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description,style = NutriCTypography.bodySm)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = time, style = NutriCTypography.bodySm, color = Colors.Neutral.color30)
        }
    }
}