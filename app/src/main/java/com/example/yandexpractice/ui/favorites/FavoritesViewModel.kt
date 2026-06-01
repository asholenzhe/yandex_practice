package com.example.yandexpractice.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yandexpractice.creator.Creator
import com.example.yandexpractice.domain.models.Track
import com.example.yandexpractice.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val favorites: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.toggleFavorite(track, false)
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}
