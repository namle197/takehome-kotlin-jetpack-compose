package com.namle197.data.repository.user

import com.namle197.common.Dispatcher
import com.namle197.common.MobileTakeHomeDispatchers
import com.namle197.database.dao.UserDao
import com.namle197.database.entity.UserEntity
import com.namle197.model.User
import com.namle197.model.UserDetail
import com.namle197.network.MobileTakeHomeDataSource
import com.namle197.network.model.ResultWrapper
import com.namle197.network.model.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val mobileTakeHomeDataSource: MobileTakeHomeDataSource,
    private val userDao: UserDao,
    @Dispatcher(MobileTakeHomeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {

    /**
     * Get users data from remote
     *
     * @param perPage: Number of users per page
     * @param since: Last user id
     *
     * @return ResultWrapper<List<User>>
     */
    override suspend fun getUsersFromRemote(perPage: Int, since: Int): ResultWrapper<List<User>> {
        val resultWrapper = safeApiCall(ioDispatcher) {
            mobileTakeHomeDataSource.getUsersByPage(perPage, since)
        }
        return resultWrapper
    }

    /**
     * Get users data from local
     *
     * @return List<User>
     */
    override suspend fun getUsersFromLocal(): List<User> {
        return userDao.getUserEntity().map {
            User(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
        }
    }

    /**
     * Save users to local database
     *
     * @param users: List<User>
     */
    override suspend fun saveUsers(users: List<User>) {
        userDao.insertUserEntities(users.map {
            UserEntity(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
        })
    }

    /**
     * Get user detail
     *
     * @param userName: String
     *
     * @return UserDetail?
     */
    override suspend fun getUserDetail(userName: String): UserDetail? {
        val resultWrapper = safeApiCall(ioDispatcher) {
            mobileTakeHomeDataSource.getUserDetail(userName)
        }
        return if (resultWrapper is ResultWrapper.Success) {
            resultWrapper.data
        } else {
            null
        }
    }
}