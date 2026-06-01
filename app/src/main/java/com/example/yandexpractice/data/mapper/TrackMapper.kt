package com.example.yandexpractice.data.mapper

import com.example.yandexpractice.data.db.entity.TrackEntity
import com.example.yandexpractice.data.dto.TrackDto
import com.example.yandexpractice.domain.models.Track

class TrackMapper {

    fun fromDto(dto: TrackDto): Track {
        return Track(
            name = dto.name,
            artistName = dto.artistName,
            timeMillis = dto.timeMillis
        )
    }

    fun fromDtoList(dtoList: List<TrackDto>): List<Track> {
        return dtoList.mapNotNull { dto ->
            if (dto.name.isBlank()) null else fromDto(dto)
        }
    }

    fun fromEntity(entity: TrackEntity): Track {
        return Track(
            id = entity.id,
            name = entity.name,
            artistName = entity.artistName,
            timeMillis = entity.timeMillis,
            favorite = entity.favorite,
            playlistId = entity.playlistId
        )
    }

    fun toEntity(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
            name = track.name,
            artistName = track.artistName,
            timeMillis = track.timeMillis,
            favorite = track.favorite,
            playlistId = track.playlistId
        )
    }
}
