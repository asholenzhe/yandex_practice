package com.example.yandexpractice.domain.repository

import com.example.yandexpractice.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(id: Long): Flow<Playlist?>
    suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?)
    suspend fun deletePlaylistById(id: Long)
}
