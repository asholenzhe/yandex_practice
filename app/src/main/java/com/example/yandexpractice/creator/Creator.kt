package com.example.yandexpractice.creator

import com.example.yandexpractice.data.network.RetrofitNetworkClient
import com.example.yandexpractice.data.repository.TracksRepositoryImpl
import com.example.yandexpractice.domain.api.TracksRepository

object Creator {

    fun getTracksRepository(): TracksRepository {
        val storage = Storage()
        val networkClient = RetrofitNetworkClient(storage)
        return TracksRepositoryImpl(networkClient)
    }
}

