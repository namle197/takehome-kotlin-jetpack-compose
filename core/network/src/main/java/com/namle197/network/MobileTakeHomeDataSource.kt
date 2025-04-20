package com.namle197.network

import com.namle197.model.User
import com.namle197.model.UserDetail

interface MobileTakeHomeDataSource {
    /**
     * Method to request users by page (pagination)
     *
     * @param perPage number of users per page
     * @param since get users since this user id
     *
     * @return list of users
     */
    suspend fun getUsersByPage(perPage: Int, since: Int): List<User>

    /**
     * Method to request user detail by id
     *
     * @param loginUserName user login name
     *
     * @return user detail
     */
    suspend fun getUserDetail(loginUserName: String): UserDetail
}