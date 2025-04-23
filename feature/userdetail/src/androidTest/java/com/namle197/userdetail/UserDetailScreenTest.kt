package com.namle197.userdetail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.namle197.testing.androidtesttag.AndroidTestTag.BLOG_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.FOLLOWER_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.LOADER_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_BACK_BUTTON_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TEXT_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.USER_ITEM_TAG
import com.namle197.testing.data.userDetailTestData
import org.junit.Rule
import org.junit.Test

class UserDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_showTopAppBar_withText_andBackIcon() {
        composeTestRule.setContent {
            UserDetailScreen (
                uiState = UserDetailUiState.Loading,
                onBack = {},
                loginUserName = userDetailTestData.login,
                avatarUrl = userDetailTestData.avatarUrl
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
            UserDetailScreen (
                uiState = UserDetailUiState.Loading,
                onBack = {},
                loginUserName = userDetailTestData.login,
                avatarUrl = userDetailTestData.avatarUrl
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
            UserDetailScreen (
                uiState = UserDetailUiState.Success(
                    userDetail = userDetailTestData
                ),
                onBack = {},
                loginUserName = userDetailTestData.login,
                avatarUrl = userDetailTestData.avatarUrl
            )
        }

        composeTestRule
            .onAllNodesWithTag(
                USER_ITEM_TAG
            )
            .assertCountEquals(1)
        composeTestRule
            .onNodeWithTag(
                FOLLOWER_TAG
            )
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(
                BLOG_TAG
            )
            .assertIsDisplayed()
    }
}