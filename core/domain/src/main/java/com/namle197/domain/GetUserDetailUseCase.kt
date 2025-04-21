package com.namle197.domain

import com.namle197.common.Dispatcher
import com.namle197.common.MobileTakeHomeDispatchers
import com.namle197.data.repository.user.UserRepository
import com.namle197.model.UserDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(MobileTakeHomeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(userName: String?): UserDetail? = withContext(ioDispatcher) {
        userName?.let {
            userRepository.getUserDetail(it)
        } ?: run {
            null
        }
    }
}