package com.example.yandexpractice.data.mapper

import com.example.yandexpractice.data.dto.TrackDto
import com.example.yandexpractice.domain.models.Track

class TrackMapper {

    fun map(dto: TrackDto): Track {
        return Track(
            name = dto.name,
            artistName = dto.artistName,
            timeMillis = dto.timeMillis
        )
    }

    fun mapList(dtoList: List<TrackDto>): List<Track> {
        return dtoList.mapNotNull { dto ->
            if (dto.name.isBlank()) {
                null
            } else {
                map(dto)
            }
        }
    }
}
