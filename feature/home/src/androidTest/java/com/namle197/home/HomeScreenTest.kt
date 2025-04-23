package com.namle197.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.namle197.testing.androidtesttag.AndroidTestTag.HOME_SCREEN_LAZY_COLUMN_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.LOADER_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_BACK_BUTTON_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TEXT_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.USER_ITEM_TAG
import com.namle197.testing.data.listUserTestData
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_showTopAppBar_withText_andBackIcon() {
        composeTestRule.setContent {
            HomeScreen (
                uiState = HomeScreenUiState.Loading,
                loadMoreState = false,
                uiEvent = "",
                onItemClick = { _, _ -> },
                onLoadMore = {},
                clearUiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithTag(
                TOP_APP_BAR_TAG
            )
            .assertExists()
        composeTestRule
            .onNodeWithTag(
                TOP_APP_BAR_TEXT_TAG
            )
            .assertExists()
        composeTestRule
            .onNodeWithContentDescription(
                TOP_APP_BAR_BACK_BUTTON_TAG
            )
            .assertExists()
    }

    @Test
    fun verify_showLoading_whenUiStateIsLoading() {
        composeTestRule.setContent {
            HomeScreen (
                uiState = HomeScreenUiState.Loading,
                loadMoreState = false,
                uiEvent = "",
                onItemClick = { _, _ -> },
                onLoadMore = {},
                clearUiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithTag(
                LOADER_TAG
            )
            .assertIsDisplayed()
    }

    @Test
    fun verify_showUsers_whenUiStateIsSuccess() {
        composeTestRule.setContent {
            HomeScreen (
                uiState = HomeScreenUiState.Success(
                    users = listUserTestData,
                    page = listUserTestData.last().id
                ),
                loadMoreState = false,
                uiEvent = "",
                onItemClick = { _, _ -> },
                onLoadMore = {},
                clearUiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithTag(
                HOME_SCREEN_LAZY_COLUMN_TAG
            )
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithTag(
                USER_ITEM_TAG
            )
            .assertCountEquals(3)
    }
}

