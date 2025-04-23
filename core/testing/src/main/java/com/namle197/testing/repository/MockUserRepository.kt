package com.namle197.testing.repository

import com.namle197.data.repository.user.UserRepository
import com.namle197.model.User
import com.namle197.model.UserDetail
import com.namle197.network.model.ErrorType
import com.namle197.network.model.ResultWrapper
import com.namle197.testing.data.listUserTestData
import com.namle197.testing.data.userDetailTestData

class MockUserRepository: UserRepository {
    private var isApiSuccess = false
    private var isLocalSuccess = false
    private val localListUser: MutableList<User> = mutableListOf()

    fun setApiSuccess(isSuccess: Boolean) {
        isApiSuccess = isSuccess
    }

    fun setLocalSuccess(isSuccess: Boolean) {
        isLocalSuccess = isSuccess
    }

    override suspend fun getUsersFromRemote(
        perPage: Int,
        since: Int
    ): ResultWrapper<List<User>> {
        return if (isApiSuccess) {
            ResultWrapper.Success(listUserTestData)
        } else {
            ResultWrapper.Error(ErrorType.HTTP)
        }
    }

    override suspend fun getUsersFromLocal(): List<User> {
        return if (isLocalSuccess) {
            listUserTestData
        } else {
            emptyList()
        }
    }

    override suspend fun saveUsers(users: List<User>) {
        localListUser.addAll(users)
    }

    override suspend fun getUserDetail(userName: String): UserDetail? {
        return if (isApiSuccess) {
            userDetailTestData
        } else {
            null
        }
    }
}