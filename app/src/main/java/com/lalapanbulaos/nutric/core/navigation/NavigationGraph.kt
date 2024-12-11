package com.lalapanbulaos.nutric.core.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalapanbulaos.nutric.features.auth.presentation.screen.AuthScreen
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthState
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.screen.HealthInfoScreen
import com.lalapanbulaos.nutric.features.home.presentation.screen.HomeScreen
import com.lalapanbulaos.nutric.features.notifications.presentation.NotificationScreen
import com.lalapanbulaos.nutric.features.onboarding.presentation.OnboardingScreen
import com.lalapanbulaos.nutric.features.profile.presentation.screen.ProfileScreen
import com.lalapanbulaos.nutric.features.scan_food.presentation.screen.ScannerScreen
import com.lalapanbulaos.nutric.features.splash_screen.presentation.SplashScreen
import com.lalapanbulaos.nutric.features.statistic.presentation.StatisticScreen
import com.lalapanbulaos.nutric.presentation.component.NutriCScaffold
import kotlinx.coroutines.runBlocking

@Composable
fun ArticleScreen() {
    Text("INI artike")
}
//
//@Composable
//fun StatiSticScreen() {
//    Text("INI statistic")
//}
//
//@Composable
//fun ProfileScreen(authViewModel: AuthViewModel = hiltViewModel(), onLogout: () -> Unit) {
//    Button(onClick = {
//        runBlocking {
//            authViewModel.logout()
//        }
//        onLogout()
//    }) {
//        Text("Logout")
//    }
//}

@Composable
fun NavGraph(
    startDestination: String = AppRoutes.Splash.route,
) {
    val navController = rememberNavController()
//    val authState = authViewModel.authState.collectAsState().value

    NavHost(navController = navController, startDestination = startDestination,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {

        composable(AppRoutes.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(AppRoutes.Auth.route) {
                    popUpTo(AppRoutes.Splash.route) { inclusive = true }
                }
            })
        }

        composable(AppRoutes.Onboarding.route) {
            OnboardingScreen {
                navController.navigate(AppRoutes.Auth.route)
            }
        }

        composable(AppRoutes.Auth.route) {
            AuthScreen(onLoginSuccess = {
                navController.navigate(AppRoutes.Home.route) {
                    popUpTo(AppRoutes.Auth.route) { inclusive = true }
                }
            },
                onRequiresHealthInfo = {
                    navController.navigate(AppRoutes.HealthInfo.route) {
                        popUpTo(AppRoutes.Auth.route) { inclusive = true }
                    }
                })
        }

        composable(AppRoutes.Home.route, enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            NutriCScaffold(navController = navController) {
                HomeScreen(navController = navController)
            }
        }

        composable(AppRoutes.ScanFood.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        350, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            ScannerScreen(onGoBack = {
                navController.popBackStack()
            })

        }

        composable(AppRoutes.Articles.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            NutriCScaffold(navController = navController) {
                ArticleScreen()
            }
        }

        composable(AppRoutes.Profile.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            NutriCScaffold(navController = navController) {
                ProfileScreen(
                    navController = navController,
                    onLogout = {
                        navController.navigate(AppRoutes.Auth.route) {
                            popUpTo(AppRoutes.Profile.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(AppRoutes.Statistics.route, enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            NutriCScaffold(navController = navController) {
                StatisticScreen()
            }
        }

        composable(AppRoutes.HealthInfo.route) {
            HealthInfoScreen(onHealthInfoAvailable = {
                navController.navigate(AppRoutes.Home.route) {
                    popUpTo(AppRoutes.HealthInfo.route) { inclusive = true }
                }
            })
        }

        composable(AppRoutes.Notification.route) {
            NotificationScreen(onGoBack = {
                navController.popBackStack()
            })
        }
    }
}