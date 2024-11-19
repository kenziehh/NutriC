package com.lalapanbulaos.nutric.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalapanbulaos.nutric.features.auth.presentation.screen.AuthScreen

@Composable
fun NavGraph(startDestination: String = "auth") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

//        composable("onboarding") {
//            OnboardingScreen {
//                navController.navigate("signin")
//            }
//        }
//
        composable("auth") {
            AuthScreen()
        }
//
//        composable("signup") {
//            SignUpScreen {
//                navController.navigate("healthinfo_step1")
//            }
//        }
//
//        composable("healthinfo") {
//            HealthInfo {
//                navController.navigate("home")
//            }
//        }
//
//        composable("home") {
//            HomeScreen(
//                onScanFood = { navController.navigate("scanfood") },
//                onArticle = { navController.navigate("articles") },
//                onMealStats = { navController.navigate("mealstats") },
//                onProfile = { navController.navigate("profile") },
//                onCamera = { navController.navigate("camera") }
//            )
//        }
//
//        composable("scanfood") {
//            ScanFoodScreen()
//        }
//
//        composable("articles") {
//            ArticleScreen {
//                navController.navigate("articleDetail/$it")
//            }
//        }
//
//        composable("articleDetail/{articleId}") { backStackEntry ->
//            val articleId = backStackEntry.arguments?.getString("articleId")?.toInt() ?: 0
//            ArticleDetailScreen(articleId = articleId)
//        }
//
//        composable("mealstats") {
//            MealStatsScreen()
//        }
//
//        composable("profile") {
//            ProfileScreen()
//        }
    }
}
