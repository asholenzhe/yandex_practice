package com.example.yandexpractice.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yandexpractice.R
import com.example.yandexpractice.ui.components.TrackListItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable(enabled = query.isNotBlank()) {
                        if (query.isNotBlank()) {
                            viewModel.search(query)
                        }
                    },
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon_cd)
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = {
                        query = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.search_clear_icon_cd)
                        )
                    }
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        when (val state = screenState) {
            is SearchState.Initial -> {
                PlaceholderMessage(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.search_initial_state)
                )
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = state.list
                if (tracks.isEmpty()) {
                    PlaceholderMessage(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.search_empty_result)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        items(items = tracks, key = { it.name + it.artistName }) { track ->
                            TrackListItem(track = track)
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                PlaceholderMessage(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.search_error_template, state.error)
                )
            }
        }
    }
}

@Composable
private fun PlaceholderMessage(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}