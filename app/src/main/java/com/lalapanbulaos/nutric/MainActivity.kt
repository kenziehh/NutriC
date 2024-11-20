package com.lalapanbulaos.nutric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.lalapanbulaos.nutric.core.navigation.NavGraph
import com.lalapanbulaos.nutric.presentation.theme.NutriCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriCTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(startDestination = "splash")
                }
            }
        }
    }
}
