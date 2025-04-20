package com.namle197.home

import com.namle197.model.User

internal sealed interface HomeScreenUiState {
    object Loading: HomeScreenUiState
    data class Success(val users: List<User>): HomeScreenUiState
}