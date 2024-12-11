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
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalapanbulaos.nutric.features.article.presentation.screen.ArticleScreen
import com.lalapanbulaos.nutric.features.auth.presentation.screen.AuthScreen
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthState
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.healthinfo.presentation.screen.HealthInfoScreen
import com.lalapanbulaos.nutric.features.home.presentation.screen.HomeScreen
import com.lalapanbulaos.nutric.features.notifications.presentation.NotificationScreen
import com.lalapanbulaos.nutric.features.onboarding.presentation.OnboardingScreen
import com.lalapanbulaos.nutric.features.onboarding.presentation.OnboardingStepsScreen
import com.lalapanbulaos.nutric.features.profile.presentation.screen.ProfileScreen
import com.lalapanbulaos.nutric.features.scan_food.presentation.screen.ScannerScreen
import com.lalapanbulaos.nutric.features.splash_screen.presentation.SplashScreen
import com.lalapanbulaos.nutric.features.statistic.presentation.StatisticScreen
import com.lalapanbulaos.nutric.presentation.component.BottomNavbar
import com.lalapanbulaos.nutric.presentation.component.NutriCScaffold

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
    viewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        Log.d("NavGraph", "Auth state changed: $authState")
        viewModel.checkAuthState()
        when (authState) {
            is AuthState.NeedOnboarding -> {
                navController.navigate(AppRoutes.Onboarding.route) {
                    popUpTo(0) { inclusive = true } // Clear the back stack
                }
            }

            is AuthState.Unauthenticated -> {
                navController.navigate(AppRoutes.Auth.route) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is AuthState.RequiresHealthInfo -> {
                navController.navigate(AppRoutes.HealthInfo.route) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is AuthState.Authenticated -> {
                navController.navigate(AppRoutes.Home.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {
                navController.navigate(AppRoutes.Splash.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

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
            SplashScreen(
                onTimeout = {
                    navController.navigate(AppRoutes.Auth.route) {
                        popUpTo(AppRoutes.Splash.route) { inclusive = true }
                    }
                },
                navController = navController
            )
        }

        composable(AppRoutes.Onboarding.route) {
            OnboardingStepsScreen(
                onFinish = {
                    navController.navigate(AppRoutes.Auth.route) {
                        popUpTo(AppRoutes.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.Auth.route) {
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(AppRoutes.Home.route) {
                        popUpTo(AppRoutes.Auth.route) { inclusive = true }
                    }
                },
                onRequiresHealthInfo = {
                    navController.navigate(AppRoutes.HealthInfo.route) {
                        popUpTo(AppRoutes.Auth.route) { inclusive = true }
                    }
                },
                navController = navController
            )
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
            Box {
                ArticleScreen()
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    BottomNavbar(navController)
                }
            }
        }

        composable(AppRoutes.ArticleDetail.route) {

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