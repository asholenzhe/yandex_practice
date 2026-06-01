package com.example.yandexpractice.domain.repository

interface SearchHistoryRepository {
    suspend fun add(query: String)
    suspend fun getHistory(): List<String>
}
