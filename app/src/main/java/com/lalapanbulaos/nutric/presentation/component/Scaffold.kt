package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.presentation.theme.Colors

data class BottomNavItem(
    val name: String,
    val iconActive: Int,
    val iconInactive: Int
)

@Composable
fun NutriCScaffold(navController: NavController, children: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        bottomBar = { BottomNavbar(navController) },
        containerColor = Colors.Background.white
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).padding(top = 40.dp, start = 24.dp, end = 24.dp).safeDrawingPadding().background(Colors.Background.white).verticalScroll(
            rememberScrollState()
        )
        ) {
            children()
        }
    }
}

@Composable
fun BottomNavbar(navController: NavController) {
    // Get the current route from NavController
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val items = listOf(
        BottomNavItem("Beranda", R.drawable.home_active, R.drawable.home_inactive),
        BottomNavItem("Statistik", R.drawable.statistic_active, R.drawable.statistic_inactive),
        BottomNavItem("Scan", R.drawable.scan, R.drawable.scan),
        BottomNavItem("Artikel", R.drawable.artikel_active, R.drawable.artikel_inactive),
        BottomNavItem("Profil", R.drawable.profile_active, R.drawable.profil_inactive)
    )

    // Determine the selected index based on the current route
    val selectedItemIndex = when (currentRoute) {
        "home" -> 0
        "statistics" -> 1
        "scanfood" -> 2
        "articles" -> 3
        "profile" -> 4
        else -> 0 // Default to "home"
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Colors.Background.white
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (index == 2) {
                        Button(
                            onClick = { navController.navigate("scanfood") },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Colors.Secondary.color50),
                            modifier = Modifier.size(72.dp)
                        ) {
                            Image(
                                painter = painterResource(id = if (selectedItemIndex == index) item.iconActive else item.iconInactive),
                                contentDescription = item.name,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                // Navigate to the corresponding screen
                                when (index) {
                                    0 -> navController.navigate("home")
                                    1 -> navController.navigate("statistics")
                                    3 -> navController.navigate("articles")
                                    4 -> navController.navigate("profile")
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = if (selectedItemIndex == index) item.iconActive else item.iconInactive),
                                contentDescription = item.name,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Text(
                        text = item.name,
                        fontSize = 12.sp,
                        color = if (selectedItemIndex == index)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
