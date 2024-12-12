package com.lalapanbulaos.nutric.features.home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.core.navigation.AppRoutes
import com.lalapanbulaos.nutric.features.home.presentation.component.ActivityCard
import com.lalapanbulaos.nutric.features.home.presentation.component.HalfCircularProgressBar
import com.lalapanbulaos.nutric.features.home.presentation.viewmodel.HomeViewModel
import com.lalapanbulaos.nutric.presentation.component.NutrientProgressBar
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun HomeScreen(
        viewModel: HomeViewModel = hiltViewModel() ,
        navController: NavController

        ) {
        val uiState by viewModel.uiState.collectAsState()
        val userName by viewModel.userName.collectAsState()

        Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column {
                                Text("Selamat Datang!", style = NutriCTypography.bodySm, color = Colors.Alomani.gray)
                                Text(userName, style = NutriCTypography.heading)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(R.drawable.notif), contentDescription = "notification", modifier = Modifier.size(24.dp).clickable {
                                        navController.navigate("notification")

                                })
                                Spacer(modifier = Modifier.width(14.dp))
                                Image(painter = painterResource(R.drawable.photo_profile), contentDescription = "profile", modifier = Modifier.size(48.dp).clickable {
                                        navController.navigate("profile")
                                })
                        }
                }
                Spacer(Modifier.height(32.dp))
                Column {
                        val totalCalories = uiState.totalMacros?.calories ?: 0
                        val totalCarbs = uiState.totalMacros?.carbohydrates ?: 0
                        val totalProteins = uiState.totalMacros?.protein ?: 0
                        val totalFats = uiState.totalMacros?.fat ?: 0
                        val targetCalories = uiState.dailyTarget?.calories ?: 2400
                        val targetCarbs = uiState.dailyTarget?.carbohydrates ?: 100
                        val targetProteins = uiState.dailyTarget?.protein ?: 100
                        val targetFats = uiState.dailyTarget?.fat ?: 100

                        Box(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(
                                                color = Color(0xFFE3EDDE),
                                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                        )
                        ) {
                                Image(
                                        painter = painterResource(id = R.drawable.bg_calories_half),
                                        contentDescription = "Background Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                )

                                Column(
                                        modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                ) {
                                        Spacer(modifier = Modifier.height(42.dp))
                                        HalfCircularProgressBar(
                                                totalCalories = totalCalories.toInt(),
                                                calorieNeeds = targetCalories.toInt()
                                        )
                                        Spacer(modifier = Modifier.height(42.dp))
                                }
                        }

                        Row(
                                modifier = Modifier.fillMaxWidth().background(Colors.Neutral.color00, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                                Row(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Karbohidrat", totalCarbs.toInt(), targetCarbs.toInt(),R.drawable.karbohidrat)
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Protein", totalProteins.toInt(), targetProteins.toInt(),R.drawable.protein)
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Lemak", totalFats.toInt(), targetFats.toInt(),R.drawable.lemak)
                                        }
                                }

                        }
                }
                Spacer(Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Aktivitas", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                        Text("Lihat Semua", style = NutriCTypography.subHeadingXs, color = Colors.Secondary.color50, modifier = Modifier.clickable {
                                navController.navigate(AppRoutes.Statistics.route)
                        })
                }
                Spacer(Modifier.height(16.dp))

//                Column {
//                        ActivityCard(currentCalories = 860, targetCalories = 2000, date = "Senin, 23 oktober 2024", foodName = "Nasi Padang", imageResource = R.drawable.activity1)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        ActivityCard(currentCalories = 400, targetCalories = 2000, date = "Senin, 23 oktober 2024", foodName = "Lalapan Bu Laos", imageResource = R.drawable.activity2)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        ActivityCard(currentCalories = 200, targetCalories = 2000, date = "Senin, 23 oktober 2024", foodName = "Momogiiiiiiiiiii", imageResource = R.drawable.activity1)
//                }

                when {
                        uiState.isLoading -> {
                                CircularProgressIndicator(
                                        color = Colors.Primary.color50,
                                        modifier = Modifier.fillMaxWidth()
                                                .wrapContentSize(Alignment.Center)
                                )
                        }
                        uiState.error != null -> {
                                Text("Something went wrong. please try again later")
                        }
                        else -> {
                                LazyColumn(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                                        if (uiState.meals.isEmpty()) {
                                                item {
                                                        Text(
                                                                text = "Belum ada aktivitas hari ini",
                                                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                                                                color = Colors.Neutral.color40,
                                                                modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(16.dp),
                                                                textAlign = TextAlign.Center
                                                        )                                                }
                                        } else {
                                                items(uiState.meals) { meal ->
                                                        meal.food.foodMacroNutrient?.calories?.let { ActivityCard(currentCalories = it.toInt(), date = "Today", foodName = meal.food.name, imageResource = R.drawable.activity1, targetCalories = 2400) }
                                                        Spacer(modifier = Modifier.height(8.dp))
                                                }
                                        }
                                }

                        }
                }
        }
}


//@Composable
//fun MealItem(meal: MealResponse) {
//        Card(
//                modifier = Modifier.fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//                Column(
//                        modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp)
//                ) {
//                        Text(
//                                text = "Meal ID: ${meal.id}",
//                                fontWeight = FontWeight.Bold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                                text = "Serving: ${meal.serving_size} ${meal.serving_unit}",
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                                text = "Created at: ${meal.created_at}",
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                                text = "Foods:",
//                                fontWeight = FontWeight.Bold
//                        )
//                                Text(
//                                        text = "- ${meal.food.name}",
//                                )
//
//                }
//        }
//}
