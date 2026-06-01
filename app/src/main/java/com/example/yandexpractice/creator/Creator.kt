package com.example.yandexpractice.creator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.yandexpractice.data.db.AppDatabase
import com.example.yandexpractice.data.mapper.TrackMapper
import com.example.yandexpractice.data.network.RetrofitNetworkClient
import com.example.yandexpractice.data.preferences.SearchHistoryPreferences
import com.example.yandexpractice.data.repository.PlaylistsRepositoryImpl
import com.example.yandexpractice.data.repository.SearchHistoryRepositoryImpl
import com.example.yandexpractice.data.repository.TracksRepositoryImpl
import com.example.yandexpractice.domain.repository.PlaylistsRepository
import com.example.yandexpractice.domain.repository.SearchHistoryRepository
import com.example.yandexpractice.domain.repository.TracksRepository

private val Context.searchHistoryDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "search_history"
)

object Creator {

    private lateinit var appContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "tracklist.db")
            .build()
    }

    private val trackMapper = TrackMapper()

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            RetrofitNetworkClient(Storage()),
            trackMapper,
            database.trackDao()
        )
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return PlaylistsRepositoryImpl(
            database.playlistDao(),
            database.trackDao(),
            trackMapper
        )
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            SearchHistoryPreferences(appContext.searchHistoryDataStore)
        )
    }
}
