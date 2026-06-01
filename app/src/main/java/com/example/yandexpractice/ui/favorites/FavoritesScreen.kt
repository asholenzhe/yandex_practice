package com.example.yandexpractice.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yandexpractice.R
import com.example.yandexpractice.domain.models.Track
import com.example.yandexpractice.ui.components.ScreenTopBar
import com.example.yandexpractice.ui.components.TrackListItem

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel,
    onTrackClick: (Track) -> Unit,
    onBack: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            ScreenTopBar(
                title = stringResource(id = R.string.favorites_title),
                onBack = onBack
            )
            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.favorites_empty))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = favorites, key = { it.id }) { track ->
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track) },
                            onLongClick = { viewModel.removeFromFavorites(track) }
                        )
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}
