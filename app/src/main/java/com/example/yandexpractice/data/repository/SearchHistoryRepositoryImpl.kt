package com.example.yandexpractice.data.repository

import com.example.yandexpractice.data.preferences.SearchHistoryPreferences
import com.example.yandexpractice.domain.repository.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override suspend fun add(query: String) {
        preferences.addEntry(query)
    }

    override suspend fun getHistory(): List<String> {
        return preferences.getEntries()
    }
}
