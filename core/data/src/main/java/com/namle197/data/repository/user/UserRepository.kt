package com.namle197.data.repository.user

import com.namle197.model.User
import com.namle197.model.UserDetail
import com.namle197.network.model.ResultWrapper

interface UserRepository {
    /**
     * Get users data from remote
     *
     * @param perPage: Number of users per page
     * @param since: Last user id
     *
     * @return ResultWrapper<List<User>>
     */
    suspend fun getUsersFromRemote(perPage: Int, since: Int): ResultWrapper<List<User>>

    /**
     * Get users data from local
     *
     * @return List<User>
     */
    suspend fun getUsersFromLocal(): List<User>

    /**
     * Save users to local database
     *
     * @param users: List<User>
     */
    suspend fun saveUsers(users: List<User>)

    /**
     * Get user detail
     *
     * @param userName: String
     *
     * @return UserDetail?
     */
    suspend fun getUserDetail(userName: String): UserDetail?

}