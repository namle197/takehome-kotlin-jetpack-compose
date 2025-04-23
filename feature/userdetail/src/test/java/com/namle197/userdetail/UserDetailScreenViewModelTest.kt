package com.namle197.userdetail

import androidx.lifecycle.SavedStateHandle
import com.namle197.domain.GetFirstTwentyUsersUseCase
import com.namle197.domain.GetNextUsersUseCase
import com.namle197.domain.GetUserDetailUseCase
import com.namle197.testing.data.listUserTestData
import com.namle197.testing.data.userDetailTestData
import com.namle197.testing.repository.MockUserRepository
import com.namle197.testing.util.MainDispatcherRule
import junit.framework.Assert.assertTrue
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
    private lateinit var mockGetUserDetailUseCase: GetUserDetailUseCase
    private lateinit var SUT: UserDetailScreenViewModel // SUT - System Under Test

    @Before
    fun setup() {
        mockGetUserDetailUseCase = GetUserDetailUseCase(mockUserRepository, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `Given api would success, when fetch user detail, then return success`() = runTest {
        mockUserRepository.setApiSuccess(true)
        mockUserRepository.setLocalSuccess(false)
        val savedStateHandle = SavedStateHandle(mapOf("loginUserName" to "testUser"))

        SUT = UserDetailScreenViewModel(mockGetUserDetailUseCase, savedStateHandle)

        advanceUntilIdle()

        assertTrue(SUT.userDetailUiState.value is UserDetailUiState.Success)
        assertEquals(userDetailTestData, (SUT.userDetailUiState.value as UserDetailUiState.Success).userDetail)
    }

    @Test
    fun `Given api would failed, when fetch user detail, then return success`() = runTest {
        mockUserRepository.setApiSuccess(false)
        mockUserRepository.setLocalSuccess(false)
        val savedStateHandle = SavedStateHandle(mapOf("loginUserName" to "testUser"))

        SUT = UserDetailScreenViewModel(mockGetUserDetailUseCase, savedStateHandle)

        advanceUntilIdle()

        assertTrue(SUT.userDetailUiState.value is UserDetailUiState.Error)
    }
}