package com.lalapanbulaos.nutric.features.home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.features.home.presentation.component.HalfCircularProgressBar
import com.lalapanbulaos.nutric.features.home.presentation.component.NutrientProgressBar
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun HomeScreen(){
        Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column {
                                Text("Selamat Datang!", style = NutriCTypography.bodySm, color = Colors.Alomani.gray)
                                Text("Nama User", style = NutriCTypography.heading)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(R.drawable.notif), contentDescription = "notification", modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(14.dp))
                                Image(painter = painterResource(R.drawable.photo_profile), contentDescription = "notification", modifier = Modifier.size(48.dp))
                        }
                }
                Spacer(Modifier.height(32.dp))
                Column {
                        Row(Modifier.fillMaxWidth().height(200.dp).background(color = Colors.Primary.color10, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))){
                                Column(
                                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                ){
                                        Spacer(modifier = Modifier.height(42.dp))
                                        HalfCircularProgressBar(totalCalories = 1840, calorieNeeds = 2500)
                                        Spacer(modifier = Modifier.height(42.dp))
                                }
                        }
                        Row(
                                modifier = Modifier.fillMaxWidth().background(Colors.Neutral.color00, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                                Row(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Karbohidrat", 40, 145,R.drawable.karbohidrat)
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Protein", 195, 190,R.drawable.protein)
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                                NutrientProgressBar("Lemak", 0, 90,R.drawable.lemak)
                                        }
                                }

                        }


                }



        }
}



