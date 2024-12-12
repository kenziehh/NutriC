package com.lalapanbulaos.nutric.core.navigation

sealed class AppRoutes(val route: String) {
    object Splash : AppRoutes("splash")
    object AuthCheck : AppRoutes("/auth/check")
    object Onboarding : AppRoutes("onboarding")
    object Auth : AppRoutes("auth")
    object Home : AppRoutes("home")
    object ScanFood : AppRoutes("scanfood")
    object Articles : AppRoutes("articles")
    object Profile : AppRoutes("profile")
    object Statistics : AppRoutes("statistics")
    object HealthInfo : AppRoutes("health-info")
    object Notification : AppRoutes("notification")
    object ArticleDetail : AppRoutes("article-detail/{articleId}") {
        fun createRoute(articleId: String): String = "article-detail/$articleId"
    }
}
