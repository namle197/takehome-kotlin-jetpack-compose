package com.namle197.domain

import com.namle197.network.model.ResultWrapper
import com.namle197.testing.repository.MockUserRepository
import com.namle197.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFirstTwentyUsersUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockUserRepository = MockUserRepository()
    private lateinit var SUT: GetFirstTwentyUsersUseCase

    @Before
    fun setup() {
        SUT = GetFirstTwentyUsersUseCase(mockUserRepository, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `Given api would success, then when call invoke, then return success`() = runTest {
        // Given
        mockUserRepository.setApiSuccess(true)

        // When
        val result = SUT.invoke()

        // Then
        assertTrue(result is ResultWrapper.Success)
        assertEquals(3, (result as ResultWrapper.Success).data.size)
    }

    @Test
    fun `Given api would fail and local would success, then when call invoke, then return success`() = runTest {
        // Given
        mockUserRepository.setApiSuccess(false)
        mockUserRepository.setLocalSuccess(true)

        // When
        val result = SUT.invoke()

        // Then
        assertTrue(result is ResultWrapper.Success)
        assertEquals(3, (result as ResultWrapper.Success).data.size)
    }

    @Test
    fun `Given both api fail, then when call invoke, then return error`() = runTest {
        // Given
        mockUserRepository.setApiSuccess(false)
        mockUserRepository.setLocalSuccess(false)

        // When
        val result = SUT.invoke()

        // Then
        assertTrue(result is ResultWrapper.Error)
    }
}