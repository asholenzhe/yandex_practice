package com.example.yandexpractice.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yandexpractice.ui.main.MainScreen
import com.example.yandexpractice.ui.search.SearchScreen
import com.example.yandexpractice.ui.search.SearchViewModel
import com.example.yandexpractice.ui.settings.SettingsScreen

enum class PlaylistScreen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings")
}

@Composable
fun PlaylistHost(navController: NavHostController) {

    fun navigateTo(screen: PlaylistScreen) {
        if (navController.currentDestination?.route != screen.route) {
            navController.navigate(screen.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = PlaylistScreen.MAIN.route
    ) {
        composable(PlaylistScreen.MAIN.route) {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                onSongsClick = { navigateTo(PlaylistScreen.SEARCH) },
                onSettingsClick = { navigateTo(PlaylistScreen.SETTINGS) }
            )
        }
        composable(PlaylistScreen.SEARCH.route) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            )
        }
        composable(PlaylistScreen.SETTINGS.route) {
            SettingsScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

