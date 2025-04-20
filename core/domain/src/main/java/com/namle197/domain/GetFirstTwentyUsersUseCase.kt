package com.namle197.domain

import com.namle197.data.repository.user.UserRepository
import com.namle197.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFirstTwentyUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> = flow {
        var users = userRepository.getUsersFromLocal()

        if (users.isEmpty()) {
            users = userRepository.getUsersFromRemote(20, 0)
            userRepository.saveUsers(users)
        }
        emit(users)
    }.flowOn(Dispatchers.IO)
}