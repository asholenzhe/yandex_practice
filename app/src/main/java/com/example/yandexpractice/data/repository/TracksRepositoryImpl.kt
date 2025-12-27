package com.example.yandexpractice.data.repository

import com.example.yandexpractice.data.dto.request.TracksSearchRequest
import com.example.yandexpractice.data.dto.response.TracksSearchResponse
import com.example.yandexpractice.data.mapper.TrackMapper
import com.example.yandexpractice.domain.api.NetworkClient
import com.example.yandexpractice.domain.repository.TracksRepository
import com.example.yandexpractice.domain.models.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackMapper: TrackMapper
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            trackMapper.mapList((response as TracksSearchResponse).results)
        } else {
            emptyList()
        }
    }
}

