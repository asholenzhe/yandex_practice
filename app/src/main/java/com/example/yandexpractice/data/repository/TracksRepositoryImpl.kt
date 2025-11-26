package com.example.yandexpractice.data.repository

import com.example.yandexpractice.data.dto.TracksSearchRequest
import com.example.yandexpractice.data.dto.TracksSearchResponse
import com.example.yandexpractice.domain.api.NetworkClient
import com.example.yandexpractice.domain.api.TracksRepository
import com.example.yandexpractice.domain.models.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1_000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val totalSeconds = dto.trackTimeMillis / 1_000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val formattedTime = "%02d:%02d".format(minutes, seconds)
                Track(dto.trackName, dto.artistName, formattedTime)
            }
        } else {
            emptyList()
        }
    }
}

