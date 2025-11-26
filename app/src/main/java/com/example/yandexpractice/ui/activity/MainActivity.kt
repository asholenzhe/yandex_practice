package com.example.yandexpractice.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yandexpractice.ui.navigation.PlaylistHost
import com.example.yandexpractice.ui.theme.YandexPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YandexPracticeTheme {
                PlaylistMakerApp()
            }
        }
    }
}

@Composable
private fun PlaylistMakerApp() {
    val navController = rememberNavController()
    PlaylistHost(navController = navController)
}

