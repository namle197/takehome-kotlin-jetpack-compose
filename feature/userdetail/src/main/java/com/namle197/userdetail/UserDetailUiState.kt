package com.namle197.userdetail

import com.namle197.model.UserDetail

internal sealed interface UserDetailUiState {
    object Loading: UserDetailUiState
    data class Success(val userDetail: UserDetail?): UserDetailUiState
}