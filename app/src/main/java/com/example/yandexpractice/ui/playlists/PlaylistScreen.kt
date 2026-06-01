package com.example.yandexpractice.ui.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yandexpractice.R
import com.example.yandexpractice.domain.models.Playlist
import com.example.yandexpractice.ui.components.ScreenTopBar
import com.example.yandexpractice.ui.components.TrackListItem

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel,
    onBack: () -> Unit
) {
    val playlist by viewModel.playlist.collectAsState(initial = null)

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            ScreenTopBar(
                title = stringResource(id = R.string.playlist_title),
                onBack = onBack,
                actions = {
                    IconButton(onClick = {
                        viewModel.deletePlaylist(onDeleted = onBack)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_playlist_cd)
                        )
                    }
                }
            )

            val current = playlist
            if (current != null) {
                PlaylistInfo(playlist = current)
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = current.tracks, key = { it.id }) { track ->
                        TrackListItem(track = track)
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistInfo(playlist: Playlist) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.size(200.dp)) {
            if (playlist.coverImageUri != null) {
                AsyncImage(
                    model = playlist.coverImageUri,
                    contentDescription = stringResource(id = R.string.playlist_cover),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_music),
                    contentDescription = stringResource(id = R.string.playlist_cover),
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        }
        Text(text = playlist.name, style = MaterialTheme.typography.headlineSmall)
        if (playlist.description.isNotBlank()) {
            Text(text = playlist.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
