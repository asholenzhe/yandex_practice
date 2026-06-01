package com.example.yandexpractice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexpractice.data.db.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE name = :name AND artistName = :artist LIMIT 1")
    suspend fun findByNameAndArtist(name: String, artist: String): TrackEntity?

    @Query("SELECT * FROM tracks WHERE favorite = 1 ORDER BY id DESC")
    fun getFavorites(): Flow<List<TrackEntity>>

    @Query("UPDATE tracks SET playlistId = 0 WHERE playlistId = :playlistId")
    suspend fun detachFromPlaylist(playlistId: Long)
}
