package com.example.yandexpractice.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yandexpractice.creator.Creator
import com.example.yandexpractice.domain.repository.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    private val _coverImageUri = MutableStateFlow<String?>(null)
    val coverImageUri = _coverImageUri.asStateFlow()

    fun setCoverImageUri(uri: String?) {
        _coverImageUri.update { uri }
    }

    fun createPlaylist(name: String, description: String, onCreated: () -> Unit) {
        if (name.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(name, description, _coverImageUri.value)
        }
        onCreated()
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewPlaylistViewModel(Creator.getPlaylistsRepository()) as T
                }
            }
    }
}
