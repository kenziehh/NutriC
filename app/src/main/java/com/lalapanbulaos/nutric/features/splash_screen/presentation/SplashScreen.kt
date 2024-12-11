package com.lalapanbulaos.nutric.features.splash_screen.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.navigation.AppRoutes
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthState
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.presentation.theme.Colors


@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val authState = viewModel.authState.collectAsState().value

    LaunchedEffect(authState) {
        kotlinx.coroutines.delay(800)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Colors.Primary.color00),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_nutric), // Replace with your image resource
            contentDescription = "Splash logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(230.dp),

            )
    }


}
