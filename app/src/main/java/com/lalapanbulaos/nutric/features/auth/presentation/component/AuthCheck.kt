package com.lalapanbulaos.nutric.features.auth.presentation.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel

@Composable
fun AuthCheck(navController: NavController, authViewModel: AuthViewModel = hiltViewModel(), healthInfoViewModel: HealthInfoViewModel = hiltViewModel()) {
   val token = authViewModel.accessToken.collectAsState(initial = "").value
    val healthInfo = healthInfoViewModel.healthInfo.collectAsState(initial = null).value
    Log.d("AuthCheck", "Token: $token")

    LaunchedEffect(token) {
        if (token == null) {
            navController.navigate("login") {
                popUpTo(0) // Clear navigation stack
            }
        } else {
            navController.navigate("home") {
                popUpTo(0) // Clear navigation stack
            }
        }
    }
}