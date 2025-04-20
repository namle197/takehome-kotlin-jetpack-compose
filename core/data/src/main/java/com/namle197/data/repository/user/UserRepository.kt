package com.namle197.data.repository.user

import com.namle197.model.User
import com.namle197.model.UserDetail
import com.namle197.network.model.ResultWrapper

interface UserRepository {
    /**
     * Get users data from remote
     */
    suspend fun getUsersFromRemote(perPage: Int, since: Int): ResultWrapper<List<User>>

    /**
     * Get users data from local
     */
    suspend fun getUsersFromLocal(): List<User>

    /**
     * Save users to local database
     */
    suspend fun saveUsers(users: List<User>)

    /**
     * Get user detail
     */
    suspend fun getUserDetail(userName: String): UserDetail?

}