package com.example.yandexpractice.data.network

import com.example.yandexpractice.creator.Storage
import com.example.yandexpractice.data.dto.request.TracksSearchRequest
import com.example.yandexpractice.data.dto.response.TracksSearchResponse
import com.example.yandexpractice.domain.api.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val searchList = storage.search((dto as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}