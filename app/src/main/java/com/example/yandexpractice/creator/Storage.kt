package com.example.yandexpractice.creator

import com.example.yandexpractice.data.dto.TrackDto

class Storage {

    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Тролль",
            trackTimeMillis = 158_000
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283_000
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312_000
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225_000
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272_000
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230_000
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296_000
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195_000
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222_000
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серёга",
            trackTimeMillis = 241_000
        )
    )

    fun search(request: String): List<TrackDto> {
        if (request.isBlank()) return emptyList()
        return listTracks.filter { track ->
            track.trackName.lowercase().contains(request.lowercase())
        }
    }
}

