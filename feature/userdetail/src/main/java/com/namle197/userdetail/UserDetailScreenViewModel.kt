package com.namle197.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namle197.domain.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UserDetailScreenViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    // Extract loginUserName from savedStateHandle
    private val loginUserName: String? = savedStateHandle.get<String>("loginUserName")

    // User detail states
    private val _userDetailUiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val userDetailUiState: StateFlow<UserDetailUiState> = _userDetailUiState

    init {
        // Fetch user detail when the ViewModel is initialized
        fetchUserDetail(userName = loginUserName)
    }

    // Function to fetch user detail
    private fun fetchUserDetail(userName: String?) {
        _userDetailUiState.update { UserDetailUiState.Loading }
        viewModelScope.launch {
            try {
                val userDetail = getUserDetailUseCase(userName)
                if (userDetail != null) {
                    _userDetailUiState.update { UserDetailUiState.Success(userDetail) }
                } else {
                    _userDetailUiState.update { UserDetailUiState.Error }
                }
            } catch (e: Exception) {
                _userDetailUiState.update { UserDetailUiState.Error }
            }
        }
    }
}