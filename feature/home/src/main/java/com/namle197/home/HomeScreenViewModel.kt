package com.namle197.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namle197.domain.GetFirstTwentyUsersUseCase
import com.namle197.domain.GetNextUsersUseCase
import com.namle197.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val getFirstTwentyUsersUseCase: GetFirstTwentyUsersUseCase,
    private val getNextUsersUseCase: GetNextUsersUseCase
): ViewModel() {
    val homeScreenUiState: StateFlow<HomeScreenUiState> =
        getFirstTwentyUsersUseCase().map(HomeScreenUiState::Success)
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeScreenUiState.Loading
            )

    suspend fun getFirstTwentyUsers() = getFirstTwentyUsersUseCase()
}