package com.example.yandexpractice.domain.api

import com.example.yandexpractice.domain.models.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}

