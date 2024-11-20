package com.lalapanbulaos.nutric.core.navigation

import android.provider.ContactsContract.Profile
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalapanbulaos.nutric.features.auth.presentation.screen.AuthScreen
import com.lalapanbulaos.nutric.features.home.presentation.HomeScreen
import com.lalapanbulaos.nutric.presentation.component.NutriCScaffold
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography


//@Composable
//fun NavGraph(
//    navController: NavController,
//    startDestination: String = "home"
//) {
//    NavHost(navController = navController, startDestination = startDestination) {
//        composable("home") {
//            HomeScreen()
//        }
//        composable("mealstats") {
////            MealStatsScreen()
//        }
//        composable("scanfood") {
////            ScanFoodScreen()
//        }
//        composable("articles") {
////            ArticleScreen {
////                navController.navigate("articleDetail/$it")
////            }
//        }
//        composable("profile") {
////            ProfileScreen()
//        }
//    }
//}
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
fun ProfileScreen() {
    Text("INI profil")
}




@Composable
fun NavGraph(startDestination: String = "home") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

//        composable("onboarding") {
//            OnboardingScreen {
//                navController.navigate("signin")
//            }
//        }

        composable("auth") {
            AuthScreen()
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
////            HomeScreen(
////                onScanFood = { navController.navigate("scanfood") },
////                onArticle = { navController.navigate("articles") },
////                onMealStats = { navController.navigate("mealstats") },
////                onProfile = { navController.navigate("profile") },
////                onCamera = { navController.navigate("camera") }
////            )
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
