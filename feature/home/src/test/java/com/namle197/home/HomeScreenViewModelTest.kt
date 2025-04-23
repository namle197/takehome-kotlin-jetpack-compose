package com.namle197.home

import com.namle197.domain.GetFirstTwentyUsersUseCase
import com.namle197.domain.GetNextUsersUseCase
import com.namle197.testing.data.listUserTestData
import com.namle197.testing.repository.MockUserRepository
import com.namle197.testing.util.MainDispatcherRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class HomeScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockUserRepository = MockUserRepository()
    private lateinit var mockGetUsersUseCase: GetFirstTwentyUsersUseCase
    private lateinit var mockGetNextUsersUseCase: GetNextUsersUseCase
    private lateinit var SUT: HomeScreenViewModel // SUT - System Under Test

    @Before
    fun setup() {
        mockGetUsersUseCase = GetFirstTwentyUsersUseCase(mockUserRepository, mainDispatcherRule.testDispatcher)
        mockGetNextUsersUseCase = GetNextUsersUseCase(mockUserRepository)
    }

    @Test
    fun `Given api would success, when call init, then return success`() = runTest {
        mockUserRepository.setApiSuccess(true)
        mockUserRepository.setLocalSuccess(false)

        SUT = HomeScreenViewModel(mockGetUsersUseCase, mockGetNextUsersUseCase)
        advanceUntilIdle()
        assertTrue(SUT.homeScreenUiState.value is HomeScreenUiState.Success)
        assertEquals(3, (SUT.homeScreenUiState.value as HomeScreenUiState.Success).users.size)
        assertEquals(listUserTestData.last().id, (SUT.homeScreenUiState.value as HomeScreenUiState.Success).users.last().id)
    }

    @Test
    fun `Given api would success, when call load more, then reload new data`() = runTest {
        mockUserRepository.setApiSuccess(true)
        mockUserRepository.setLocalSuccess(false)

        SUT = HomeScreenViewModel(mockGetUsersUseCase, mockGetNextUsersUseCase)
        advanceUntilIdle()
        assertTrue(SUT.homeScreenUiState.value is HomeScreenUiState.Success)
        assertEquals(3, (SUT.homeScreenUiState.value as HomeScreenUiState.Success).users.size)
        assertEquals(listUserTestData.last().id, (SUT.homeScreenUiState.value as HomeScreenUiState.Success).users.last().id)

        SUT.loadMore()
        advanceUntilIdle()
        assertTrue(SUT.homeScreenUiState.value is HomeScreenUiState.Success)
        assertEquals(6, (SUT.homeScreenUiState.value as HomeScreenUiState.Success).users.size)
    }

    @Test
    fun `Given api would fail, when call init, then return error`() = runTest {
        mockUserRepository.setApiSuccess(false)
        mockUserRepository.setLocalSuccess(false)

        SUT = HomeScreenViewModel(mockGetUsersUseCase, mockGetNextUsersUseCase)
        advanceUntilIdle()
        assertTrue(SUT.homeScreenUiState.value is HomeScreenUiState.Error)
    }

    @Test
    fun `Given api would fail, when call clearUiEvent, then should return empty string`() = runTest {
        mockUserRepository.setApiSuccess(false)
        mockUserRepository.setLocalSuccess(false)

        SUT = HomeScreenViewModel(mockGetUsersUseCase, mockGetNextUsersUseCase)

        val collectedEvents = mutableListOf<String>()
        val collectJob = launch {
            SUT.uiEvent.collect {
                collectedEvents.add(it)
            }
        }
        advanceUntilIdle()
        assertTrue(SUT.homeScreenUiState.value is HomeScreenUiState.Error)

        SUT.clearUiEvent()
        advanceUntilIdle()
        assertEquals(listOf(""), collectedEvents)
        collectJob.cancel()
    }
}