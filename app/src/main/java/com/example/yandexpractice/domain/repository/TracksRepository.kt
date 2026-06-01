package com.example.yandexpractice.domain.repository

import com.example.yandexpractice.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun isFavorite(track: Track): Boolean
    suspend fun toggleFavorite(track: Track, isFavorite: Boolean)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Long)
}
