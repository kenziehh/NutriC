package com.lalapanbulaos.nutric.core.navigation

import android.provider.ContactsContract.Profile
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalapanbulaos.nutric.features.auth.presentation.component.AuthCheck
import com.lalapanbulaos.nutric.features.auth.presentation.screen.AuthScreen
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.screen.HealthInfoScreen
import com.lalapanbulaos.nutric.features.home.presentation.HomeScreen
import com.lalapanbulaos.nutric.features.onboarding.presentation.OnboardingScreen
import com.lalapanbulaos.nutric.features.splash_screen.presentation.SplashScreen
import com.lalapanbulaos.nutric.presentation.component.NutriCScaffold
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ScanFoodScreen(){
    Text("INI scan")
}

@Composable
fun ArticleScreen(){
    Text("INI artike")
}
@Composable
fun StatiSticScreen() {
    Text("INI statistic")
}
@Composable
fun ProfileScreen(authViewModel: AuthViewModel = hiltViewModel()) {
    Text("INI profil")

    Button(onClick = {
        authViewModel.removeAccessToken()
    }) {
        Text("Logout")
    }
}

@Composable
fun NavGraph(startDestination: String = "splash") {
    val navController = rememberNavController()

    AuthCheck(navController)

    NavHost(navController = navController, startDestination = startDestination) {

        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("onboarding") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        composable("onboarding") {
            OnboardingScreen {
                navController.navigate("auth")
            }
        }

        composable("auth") {
            AuthScreen(navController = navController)
        }

        composable("home") {
            NutriCScaffold(navController = navController) {
                HomeScreen()
            }
        }
        composable("scanfood") {
            NutriCScaffold(navController = navController) {
                ScanFoodScreen()
            }
        }
        composable("articles") {
            NutriCScaffold(navController = navController) {
                ArticleScreen()
            }
        }
        composable("profile") {
            NutriCScaffold(navController = navController) {
                ProfileScreen()
            }
        }

        composable("statistics") {
            NutriCScaffold(navController = navController) {
                StatiSticScreen()
            }
        }

        composable("healthinfo") {
            HealthInfoScreen()
        }
    }
}
