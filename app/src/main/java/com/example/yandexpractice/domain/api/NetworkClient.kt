package com.example.yandexpractice.domain.api

import com.example.yandexpractice.data.dto.response.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}