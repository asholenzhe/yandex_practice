package com.example.yandexpractice.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yandexpractice.domain.models.Track
import com.example.yandexpractice.ui.favorites.FavoritesScreen
import com.example.yandexpractice.ui.favorites.FavoritesViewModel
import com.example.yandexpractice.ui.main.MainScreen
import com.example.yandexpractice.ui.playlists.NewPlaylistScreen
import com.example.yandexpractice.ui.playlists.NewPlaylistViewModel
import com.example.yandexpractice.ui.playlists.PlaylistScreen
import com.example.yandexpractice.ui.playlists.PlaylistViewModel
import com.example.yandexpractice.ui.playlists.PlaylistsScreen
import com.example.yandexpractice.ui.playlists.PlaylistsViewModel
import com.example.yandexpractice.ui.search.SearchScreen
import com.example.yandexpractice.ui.search.SearchViewModel
import com.example.yandexpractice.ui.settings.SettingsScreen
import com.example.yandexpractice.ui.track.TrackDetailsScreen
import com.example.yandexpractice.ui.track.TrackDetailsViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {

    fun navigateToTrack(track: Track) {
        val route = "${Destination.TRACK_DETAILS.route}/" +
            "${Uri.encode(track.name)}/${Uri.encode(track.artistName)}/${track.timeMillis}"
        navController.navigate(route)
    }

    NavHost(
        navController = navController,
        startDestination = Destination.MAIN.route
    ) {
        composable(Destination.MAIN.route) {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                onSearchClick = { navController.navigate(Destination.SEARCH.route) },
                onPlaylistsClick = { navController.navigate(Destination.PLAYLISTS.route) },
                onFavoritesClick = { navController.navigate(Destination.FAVORITES.route) },
                onSettingsClick = { navController.navigate(Destination.SETTINGS.route) }
            )
        }

        composable(Destination.SEARCH.route) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onTrackClick = ::navigateToTrack,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Destination.SETTINGS.route) {
            SettingsScreen(modifier = Modifier.fillMaxSize())
        }

        composable(Destination.PLAYLISTS.route) {
            val viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.getViewModelFactory())
            PlaylistsScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onAddPlaylist = { navController.navigate(Destination.NEW_PLAYLIST.route) },
                onPlaylistClick = { id -> navController.navigate("${Destination.PLAYLIST.route}/$id") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destination.NEW_PLAYLIST.route) {
            val viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.getViewModelFactory())
            NewPlaylistScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Destination.PLAYLIST.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModel.getViewModelFactory(id))
            PlaylistScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destination.FAVORITES.route) {
            val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.getViewModelFactory())
            FavoritesScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onTrackClick = ::navigateToTrack,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Destination.TRACK_DETAILS.route}/{name}/{artist}/{time}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("artist") { type = NavType.StringType },
                navArgument("time") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name").orEmpty()
            val artist = backStackEntry.arguments?.getString("artist").orEmpty()
            val time = backStackEntry.arguments?.getInt("time") ?: 0
            val viewModel: TrackDetailsViewModel =
                viewModel(factory = TrackDetailsViewModel.getViewModelFactory(name, artist, time))
            TrackDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
