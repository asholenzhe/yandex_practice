package com.example.yandexpractice.data.dto.response

import com.example.yandexpractice.data.dto.TrackDto

class TracksSearchResponse(
    val results: List<TrackDto>
) : BaseResponse()