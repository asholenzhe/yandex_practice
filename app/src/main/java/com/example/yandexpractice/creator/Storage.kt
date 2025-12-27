package com.example.yandexpractice.creator

import com.example.yandexpractice.data.dto.TrackDto

class Storage {

    private val listTracks = listOf(
        TrackDto(
            name = "Владивосток 2000",
            artistName = "Мумий Тролль",
            timeMillis = 158_000
        ),
        TrackDto(
            name = "Группа крови",
            artistName = "Кино",
            timeMillis = 283_000
        ),
        TrackDto(
            name = "Не смотри назад",
            artistName = "Ария",
            timeMillis = 312_000
        ),
        TrackDto(
            name = "Звезда по имени Солнце",
            artistName = "Кино",
            timeMillis = 225_000
        ),
        TrackDto(
            name = "Лондон",
            artistName = "Аквариум",
            timeMillis = 272_000
        ),
        TrackDto(
            name = "На заре",
            artistName = "Альянс",
            timeMillis = 230_000
        ),
        TrackDto(
            name = "Перемен",
            artistName = "Кино",
            timeMillis = 296_000
        ),
        TrackDto(
            name = "Розовый фламинго",
            artistName = "Сплин",
            timeMillis = 195_000
        ),
        TrackDto(
            name = "Танцевать",
            artistName = "Мельница",
            timeMillis = 222_000
        ),
        TrackDto(
            name = "Чёрный бумер",
            artistName = "Серёга",
            timeMillis = 241_000
        )
    )

    fun search(request: String): List<TrackDto> {
        if (request.isBlank()) return emptyList()
        val result = listTracks.filter {
            it.name
                .lowercase()
                .contains(request.lowercase())
        }
        return result
    }
}

