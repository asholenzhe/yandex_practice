package com.example.yandexpractice.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yandexpractice.creator.Creator
import com.example.yandexpractice.domain.repository.SearchHistoryRepository
import com.example.yandexpractice.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches = _recentSearches.asStateFlow()

    init {
        loadRecentSearches()
    }

    fun search(request: String) {
        if (request.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _searchScreenState.update { SearchState.Searching }

            runCatching {
                tracksRepository.searchTracks(request)
            }.onSuccess { tracks ->
                searchHistoryRepository.add(request)
                loadRecentSearches()
                _searchScreenState.update { SearchState.Success(tracks) }
            }.onFailure { error ->
                _searchScreenState.update { SearchState.Fail(error.message.orEmpty()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            _recentSearches.update { searchHistoryRepository.getHistory() }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                        Creator.getTracksRepository(),
                        Creator.getSearchHistoryRepository()
                    ) as T
                }
            }
    }
}
