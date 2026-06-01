package com.example.yandexpractice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val artistName: String,
    val timeMillis: Int,
    val favorite: Boolean = false,
    val playlistId: Long = 0
)
