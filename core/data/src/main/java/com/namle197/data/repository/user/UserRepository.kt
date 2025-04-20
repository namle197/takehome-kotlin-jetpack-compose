package com.namle197.data.repository.user

import com.namle197.model.User

interface UserRepository {
    /**
     * Get users data from remote
     */
    suspend fun getUsersFromRemote(perPage: Int, since: Int): List<User>

    /**
     * Get users data from local
     */
    suspend fun getUsersFromLocal(): List<User>

    /**
     * Save users to local database
     */
    suspend fun saveUsers(users: List<User>)
}