package com.namle197.domain

import com.namle197.common.Dispatcher
import com.namle197.common.MobileTakeHomeDispatchers
import com.namle197.data.repository.user.UserRepository
import com.namle197.model.User
import com.namle197.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Get first twenty users use case - use in HomeViewModel
class GetFirstTwentyUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(MobileTakeHomeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ResultWrapper<List<User>> = withContext(ioDispatcher) {
        val users = userRepository.getUsersFromLocal()

        if (users.isEmpty()) {
            when(val remoteResult = userRepository.getUsersFromRemote(20, 0)) {
                is ResultWrapper.Success -> {
                    userRepository.saveUsers(remoteResult.data)
                    remoteResult
                }
                else -> {
                    return@withContext remoteResult
                }
            }
        } else {
            ResultWrapper.Success(users)
        }
    }
}