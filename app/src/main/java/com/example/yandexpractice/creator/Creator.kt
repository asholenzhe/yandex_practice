package com.example.yandexpractice.creator

import com.example.yandexpractice.data.mapper.TrackMapper
import com.example.yandexpractice.data.network.RetrofitNetworkClient
import com.example.yandexpractice.data.repository.TracksRepositoryImpl
import com.example.yandexpractice.domain.repository.TracksRepository

object Creator {

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            RetrofitNetworkClient(Storage()),
            TrackMapper()
        )
    }
}

