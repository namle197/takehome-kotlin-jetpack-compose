package com.namle197.data.repository.user

import com.namle197.common.Dispatcher
import com.namle197.common.MobileTakeHomeDispatchers
import com.namle197.database.dao.UserDao
import com.namle197.database.entity.UserEntity
import com.namle197.model.User
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
    /*private val _user: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    override val users: StateFlow<List<User>> = _user*/

    override suspend fun getUsersFromRemote(perPage: Int, since: Int): List<User> {
        val resultWrapper = safeApiCall(ioDispatcher) {
            mobileTakeHomeDataSource.getUsersByPage(perPage, since)
        }
        return if (resultWrapper is ResultWrapper.Success) {
            resultWrapper.data
        } else {
            listOf()
        }
    }

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
}