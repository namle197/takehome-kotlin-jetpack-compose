package com.namle197.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namle197.common.stringconstant.StringConstants.API_ERROR
import com.namle197.domain.GetFirstTwentyUsersUseCase
import com.namle197.domain.GetNextUsersUseCase
import com.namle197.model.User
import com.namle197.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val getFirstTwentyUsersUseCase: GetFirstTwentyUsersUseCase,
    private val getNextUsersUseCase: GetNextUsersUseCase
) : ViewModel() {
    private companion object {
        const val LOAD_MORE_LIMITATION = 20
    }

    // Common state for the home screen
    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()

    // State for load more action
    private val _loadMoreState = MutableStateFlow(false)
    val loadMoreState: StateFlow<Boolean> = _loadMoreState.asStateFlow()

    // Toast event
    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent.asSharedFlow()

    private var page = 1
    private val _users = mutableListOf<User>()

    init {
        // First time load data
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            when (val getUsersResult = getFirstTwentyUsersUseCase()) {
                is ResultWrapper.Success -> {
                    // If success then update the state
                    if (getUsersResult.data.isNotEmpty()) {
                        _users.addAll(getUsersResult.data)
                        page = getUsersResult.data.lastOrNull()?.id ?: page

                        _homeScreenUiState.value =
                            HomeScreenUiState.Success(_users, page = page)
                    }
                }

                is ResultWrapper.Error -> {
                    // If failed then emit the error
                    _uiEvent.emit(API_ERROR)

                    // change the state
                    _homeScreenUiState.value = HomeScreenUiState.Error
                }
            }
        }
    }

    // Load more items
    fun loadMore() {
        // If the current state is success, so update the state (show the loading icon)
        if (_homeScreenUiState.value is HomeScreenUiState.Success) {
            _loadMoreState.update { true }
        }
        viewModelScope.launch {
            when (val getNextResult =
                getNextUsersUseCase(perPage = LOAD_MORE_LIMITATION, since = page)) {
                is ResultWrapper.Success -> {
                    // If success then update the state
                    if (getNextResult.data.isNotEmpty()) {
                        _users.addAll(getNextResult.data)
                        page = getNextResult.data.lastOrNull()?.id ?: page

                        _homeScreenUiState.value = HomeScreenUiState.Success(_users, page = page)

                        // Turn off the loading icon
                        _loadMoreState.update { false }
                    }
                }

                is ResultWrapper.Error -> {
                    _uiEvent.emit(API_ERROR)

                    // Turn off the loading icon
                    _loadMoreState.update { false }
                }
            }
        }
    }

    // Clear the toast event
    fun clearUiEvent() {
        viewModelScope.launch {
            _uiEvent.emit("")
        }
    }
}