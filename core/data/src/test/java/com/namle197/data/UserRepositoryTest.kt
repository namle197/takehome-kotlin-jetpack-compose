package com.namle197.data

import com.namle197.data.repository.user.DefaultUserRepository
import com.namle197.database.dao.UserDao
import com.namle197.database.entity.UserEntity
import com.namle197.network.model.ResultWrapper
import com.namle197.testing.data.listUserTestData
import com.namle197.testing.data.userDetailTestData
import com.namle197.testing.network.MockMobileTakeHomeDataSource
import com.namle197.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockUserDao = MockUserDao()
    lateinit var SUT: DefaultUserRepository

    @Before
    fun setup() {
        SUT = DefaultUserRepository(
            mobileTakeHomeDataSource = MockMobileTakeHomeDataSource(),
            userDao = mockUserDao,
            ioDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @After
    fun tearDown() {
        mockUserDao.clearUsers()
    }

    @Test
    fun `Given a list of user data, when save users to local, then get users success`() = runTest {
        SUT.saveUsers(listUserTestData)
        val result = SUT.getUsersFromLocal()
        assertEquals(listUserTestData, result)
    }

    @Test
    fun `Given api would success, when get users from remote, then get users success`() = runTest {
        val result = SUT.getUsersFromRemote(perPage = 20, since = 0)
        advanceUntilIdle()
        assertTrue(result is ResultWrapper.Success)
        assertEquals(listUserTestData, (result as ResultWrapper.Success).data)
    }

    @Test
    fun `Given api would success, when get user detail from remote, then get user success`() = runTest {
        val result = SUT.getUserDetail(userName = "login1")
        advanceUntilIdle()
        assertEquals(userDetailTestData, result)
    }
}

class MockUserDao : UserDao {
    private val userEntities = mutableListOf<UserEntity>()

    override fun getUserEntity(): List<UserEntity> {
        return userEntities.toList() // Return a copy
    }

    override suspend fun insertUserEntities(userEntities: List<UserEntity>) {
        this.userEntities.addAll(userEntities)
    }

    // Helper functions for setting up test data
    fun clearUsers() {
        userEntities.clear()
    }

    fun addUser(userEntity: UserEntity) {
        userEntities.add(userEntity)
    }
}