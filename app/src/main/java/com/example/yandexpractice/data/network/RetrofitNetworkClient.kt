package com.example.yandexpractice.data.network

import com.example.yandexpractice.creator.Storage
import com.example.yandexpractice.data.dto.TracksSearchRequest
import com.example.yandexpractice.data.dto.TracksSearchResponse
import com.example.yandexpractice.domain.api.NetworkClient

class RetrofitNetworkClient(
    private val storage: Storage
) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val request = dto as TracksSearchRequest
        val searchList = storage.search(request.expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}

