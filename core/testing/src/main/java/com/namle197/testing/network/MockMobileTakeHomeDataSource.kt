package com.namle197.testing.network

import com.namle197.model.User
import com.namle197.model.UserDetail
import com.namle197.network.MobileTakeHomeDataSource
import com.namle197.testing.data.listUserTestData
import com.namle197.testing.data.userDetailTestData

class MockMobileTakeHomeDataSource: MobileTakeHomeDataSource {
    override suspend fun getUsersByPage(perPage: Int, since: Int): List<User> {
        return listUserTestData
    }

    override suspend fun getUserDetail(loginUserName: String): UserDetail {
        return userDetailTestData
    }

}