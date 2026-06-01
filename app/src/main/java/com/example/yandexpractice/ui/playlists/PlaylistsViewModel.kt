package com.example.yandexpractice.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexpractice.creator.Creator
import com.example.yandexpractice.domain.models.Playlist
import com.example.yandexpractice.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(Creator.getPlaylistsRepository()) as T
                }
            }
    }
}
