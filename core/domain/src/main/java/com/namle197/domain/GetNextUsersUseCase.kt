package com.namle197.domain

import com.namle197.data.repository.user.UserRepository
import com.namle197.model.User
import com.namle197.network.model.ResultWrapper
import javax.inject.Inject

class GetNextUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(perPage: Int, since: Int): ResultWrapper<List<User>> {
        return userRepository.getUsersFromRemote(perPage, since)
    }
}