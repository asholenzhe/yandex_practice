package com.example.yandexpractice.data.repository

import com.example.yandexpractice.data.db.dao.TrackDao
import com.example.yandexpractice.data.dto.request.TracksSearchRequest
import com.example.yandexpractice.data.dto.response.TracksSearchResponse
import com.example.yandexpractice.data.mapper.TrackMapper
import com.example.yandexpractice.domain.api.NetworkClient
import com.example.yandexpractice.domain.models.Track
import com.example.yandexpractice.domain.repository.TracksRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackMapper: TrackMapper,
    private val trackDao: TrackDao
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            trackMapper.fromDtoList((response as TracksSearchResponse).results)
        } else {
            emptyList()
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavorites().map { entities ->
            entities.map(trackMapper::fromEntity)
        }
    }

    override suspend fun isFavorite(track: Track): Boolean {
        return trackDao.findByNameAndArtist(track.name, track.artistName)?.favorite == true
    }

    override suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        val existing = trackDao.findByNameAndArtist(track.name, track.artistName)
        val entity = (existing ?: trackMapper.toEntity(track)).copy(favorite = isFavorite)
        trackDao.upsert(entity)
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) {
        val existing = trackDao.findByNameAndArtist(track.name, track.artistName)
        val entity = (existing ?: trackMapper.toEntity(track)).copy(playlistId = playlistId)
        trackDao.upsert(entity)
    }
}
