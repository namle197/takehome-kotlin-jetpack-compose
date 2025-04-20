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

    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()

    private val _loadMoreState = MutableStateFlow(false)
    val loadMoreState: StateFlow<Boolean> = _loadMoreState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent.asSharedFlow()

    private var page = 1
    private val _users = mutableListOf<User>()

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            when (val getUsersResult = getFirstTwentyUsersUseCase()) {
                is ResultWrapper.Success -> {
                    if (getUsersResult.data.isNotEmpty()) {
                        _users.addAll(getUsersResult.data)
                        page = getUsersResult.data.lastOrNull()?.id ?: page

                        _homeScreenUiState.value =
                            HomeScreenUiState.Success(_users, page = page)
                    }
                }

                is ResultWrapper.Error -> {
                    _uiEvent.emit(API_ERROR)
                    _homeScreenUiState.value = HomeScreenUiState.Error
                }
            }
        }
    }

    fun loadMore() {
        if (_homeScreenUiState.value is HomeScreenUiState.Success) {
            _loadMoreState.update { true }
        }
        viewModelScope.launch {
            when (val getNextResult =
                getNextUsersUseCase(perPage = LOAD_MORE_LIMITATION, since = page)) {
                is ResultWrapper.Success -> {
                    if (getNextResult.data.isNotEmpty()) {
                        _users.addAll(getNextResult.data)
                        page = getNextResult.data.lastOrNull()?.id ?: page

                        _homeScreenUiState.value = HomeScreenUiState.Success(_users, page = page)
                        _loadMoreState.update { false }
                    }
                }

                is ResultWrapper.Error -> {
                    _uiEvent.emit(API_ERROR)
                    _loadMoreState.update { false }
                }
            }
        }
    }

    fun clearUiEvent() {
        viewModelScope.launch {
            _uiEvent.emit("")
        }
    }
}