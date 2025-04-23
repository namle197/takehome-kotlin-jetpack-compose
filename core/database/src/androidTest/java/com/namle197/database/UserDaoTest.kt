package com.namle197.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.namle197.database.dao.UserDao
import com.namle197.database.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var SUT: UserDao
    private lateinit var takeHomeDatabase: TakeHomeDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        takeHomeDatabase = Room.inMemoryDatabaseBuilder(
            context,
            TakeHomeDatabase::class.java
        ).build()
        SUT = takeHomeDatabase.userDao()
    }

    @After
    fun tearDown() {
        takeHomeDatabase.close()
    }


    @Test
    fun getUserEntity_lessThanLimit_returnsAllUsersOrderedById() = runBlocking {
        val user1 = UserEntity(id = 2, login = "user2", avatarUrl = "url2", htmlUrl = "html2")
        val user2 = UserEntity(id = 1, login = "user1", avatarUrl = "url1", htmlUrl = "html1")
        SUT.insertUserEntities(listOf(user1, user2))

        val users = SUT.getUserEntity()
        assertEquals(2, users.size)
        assertEquals(1, users[0].id)
        assertEquals(2, users[1].id)
    }

    @Test
    fun getUserEntity_moreThanLimit_returnsFirst20OrderedById() = runBlocking {
        val usersToInsert = (1..25).map {
            UserEntity(id = it, login = "user$it", avatarUrl = "url$it", htmlUrl = "html$it")
        }
        SUT.insertUserEntities(usersToInsert)

        val users = SUT.getUserEntity()
        assertEquals(20, users.size)
        assertEquals(1, users[0].id)
        assertEquals(20, users[19].id)
    }

    @Test
    fun insertUserEntities_singleUser_userIsRetrievable() = runBlocking {
        val user = UserEntity(id = 1, login = "user1", avatarUrl = "url1", htmlUrl = "html1")
        SUT.insertUserEntities(listOf(user))

        val retrievedUsers = SUT.getUserEntity()
        assertEquals(1, retrievedUsers.size)
        assertEquals(user, retrievedUsers[0])
    }

    @Test
    fun insertUserEntities_multipleUsers_allUsersAreRetrievableAndOrdered() = runBlocking {
        val user1 = UserEntity(id = 3, login = "user3", avatarUrl = "url3", htmlUrl = "html3")
        val user2 = UserEntity(id = 1, login = "user1", avatarUrl = "url1", htmlUrl = "html1")
        val user3 = UserEntity(id = 2, login = "user2", avatarUrl = "url2", htmlUrl = "html2")
        SUT.insertUserEntities(listOf(user1, user2, user3))

        val retrievedUsers = SUT.getUserEntity()
        assertEquals(3, retrievedUsers.size)
        assertEquals(1, retrievedUsers[0].id)
        assertEquals(2, retrievedUsers[1].id)
        assertEquals(3, retrievedUsers[2].id)
    }

    @Test
    fun insertUserEntities_onConflictReplace_existingUserIsReplaced() = runBlocking {
        val initialUser = UserEntity(id = 1, login = "old_login", avatarUrl = "old_url", htmlUrl = "old_html")
        SUT.insertUserEntities(listOf(initialUser))

        val newUser = UserEntity(id = 1, login = "new_login", avatarUrl = "new_url", htmlUrl = "new_html")
        SUT.insertUserEntities(listOf(newUser))

        val retrievedUsers = SUT.getUserEntity()
        assertEquals(1, retrievedUsers.size)
        assertEquals(newUser, retrievedUsers[0])
    }

    @Test
    fun insertUserEntities_onConflictReplace_newUsersAreAlsoInserted() = runBlocking {
        val user1 = UserEntity(id = 1, login = "user1", avatarUrl = "url1", htmlUrl = "html1")
        val user2 = UserEntity(id = 2, login = "user2", avatarUrl = "url2", htmlUrl = "html2")
        SUT.insertUserEntities(listOf(user1))
        SUT.insertUserEntities(listOf(user2))

        val retrievedUsers = SUT.getUserEntity()
        assertEquals(2, retrievedUsers.size)
        assertEquals(user1, retrievedUsers[0])
        assertEquals(user2, retrievedUsers[1])
    }
}