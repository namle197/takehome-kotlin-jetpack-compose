package com.namle197.domain

import com.namle197.network.model.ResultWrapper
import com.namle197.testing.data.userDetailTestData
import com.namle197.testing.repository.MockUserRepository
import com.namle197.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetUserDetailUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockUserRepository = MockUserRepository()
    private lateinit var SUT: GetUserDetailUseCase

    @Before
    fun setup() {
        SUT = GetUserDetailUseCase(mockUserRepository, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `Given api would success, then when call invoke, then return enough data`() = runTest {
        mockUserRepository.setApiSuccess(true)
        // Given
        val userName = "testUser"
        val expectedUserDetail = userDetailTestData

        // When
        val result = SUT(userName)

        // Then
        assertEquals(expectedUserDetail, result)
    }

    @Test
    fun `Given api would fail, then when call invoke, then return enough data`() = runTest {
        mockUserRepository.setApiSuccess(false)
        // Given
        val userName = "testUser"
        val expectedUserDetail = null

        // When
        val result = SUT(userName)

        // Then
        assertEquals(expectedUserDetail, result)
    }
}