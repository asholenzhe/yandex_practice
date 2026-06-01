package com.example.yandexpractice.domain.models

data class Track(
    val id: Long = 0,
    val name: String,
    val artistName: String,
    val timeMillis: Int,
    val favorite: Boolean = false,
    val playlistId: Long = 0
)
