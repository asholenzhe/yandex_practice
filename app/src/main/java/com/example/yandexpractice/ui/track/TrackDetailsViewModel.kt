package com.example.yandexpractice.ui.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yandexpractice.creator.Creator
import com.example.yandexpractice.domain.models.Playlist
import com.example.yandexpractice.domain.models.Track
import com.example.yandexpractice.domain.repository.PlaylistsRepository
import com.example.yandexpractice.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrackDetailsViewModel(
    private val tracksRepository: TracksRepository,
    playlistsRepository: PlaylistsRepository,
    val track: Track
) : ViewModel() {

    private val _favorite = MutableStateFlow(false)
    val favorite = _favorite.asStateFlow()

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favorite.update { tracksRepository.isFavorite(track) }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val newValue = !_favorite.value
            tracksRepository.toggleFavorite(track, newValue)
            _favorite.update { newValue }
        }
    }

    fun addToPlaylist(playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.addTrackToPlaylist(track, playlistId)
        }
    }

    companion object {
        fun getViewModelFactory(name: String, artist: String, time: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val track = Track(name = name, artistName = artist, timeMillis = time)
                    return TrackDetailsViewModel(
                        Creator.getTracksRepository(),
                        Creator.getPlaylistsRepository(),
                        track
                    ) as T
                }
            }
    }
}
