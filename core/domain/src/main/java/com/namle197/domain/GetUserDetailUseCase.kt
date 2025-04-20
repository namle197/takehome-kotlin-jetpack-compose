package com.namle197.domain

import com.namle197.data.repository.user.UserRepository
import com.namle197.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userName: String?): Flow<UserDetail?> = flow {
        userName?.let {
            val result = userRepository.getUserDetail(userName)
            emit(result)
        } ?: run {
            emit(null)
        }
    }.flowOn(Dispatchers.IO)
}