package com.example.yandexpractice.data.repository

import com.example.yandexpractice.data.db.dao.PlaylistDao
import com.example.yandexpractice.data.db.dao.TrackDao
import com.example.yandexpractice.data.db.entity.PlaylistEntity
import com.example.yandexpractice.data.db.entity.PlaylistWithTracks
import com.example.yandexpractice.data.mapper.TrackMapper
import com.example.yandexpractice.domain.models.Playlist
import com.example.yandexpractice.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val trackDao: TrackDao,
    private val trackMapper: TrackMapper
) : PlaylistsRepository {

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllWithTracks().map { list ->
            list.map(::toDomain)
        }
    }

    override fun getPlaylist(id: Long): Flow<Playlist?> {
        return playlistDao.getWithTracksById(id).map { it?.let(::toDomain) }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        playlistDao.insert(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = coverImageUri
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        trackDao.detachFromPlaylist(id)
        playlistDao.deleteById(id)
    }

    private fun toDomain(item: PlaylistWithTracks): Playlist {
        return Playlist(
            id = item.playlist.id,
            name = item.playlist.name,
            description = item.playlist.description,
            coverImageUri = item.playlist.coverImageUri,
            tracks = item.tracks.map(trackMapper::fromEntity)
        )
    }
}
