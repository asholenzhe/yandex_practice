package com.example.yandexpractice.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val historyKey = stringPreferencesKey("search_history")

    suspend fun addEntry(word: String) {
        if (word.isBlank()) return
        dataStore.edit { preferences ->
            val history = preferences[historyKey]
                .orEmpty()
                .split(SEPARATOR)
                .filter { it.isNotBlank() }
                .toMutableList()
            history.remove(word)
            history.add(0, word)
            preferences[historyKey] = history.take(MAX_ENTRIES).joinToString(SEPARATOR)
        }
    }

    suspend fun getEntries(): List<String> {
        val preferences = dataStore.data.first()
        return preferences[historyKey]
            .orEmpty()
            .split(SEPARATOR)
            .filter { it.isNotBlank() }
    }

    private companion object {
        const val MAX_ENTRIES = 10
        const val SEPARATOR = "\n"
    }
}
