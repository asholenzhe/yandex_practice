package com.example.yandexpractice.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yandexpractice.data.db.dao.PlaylistDao
import com.example.yandexpractice.data.db.dao.TrackDao
import com.example.yandexpractice.data.db.entity.PlaylistEntity
import com.example.yandexpractice.data.db.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}
