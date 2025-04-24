package com.namle197.home

import com.namle197.model.User

// UI state for the home screen
// Define 3 states: Loading, Success, Error
internal sealed interface HomeScreenUiState {
    object Loading: HomeScreenUiState
    data class Success(
        val users: List<User>,
        val endReached: Boolean = false,
        val page: Int = 0
    ): HomeScreenUiState

    object Error: HomeScreenUiState
}