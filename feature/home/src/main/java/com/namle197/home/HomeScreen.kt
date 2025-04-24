package com.namle197.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.namle197.common.stringconstant.StringConstants.HOME_SCREEN_TOP_BAR_LABEL
import com.namle197.designsystem.ui.ScreenScaffold
import com.namle197.testing.androidtesttag.AndroidTestTag.COLUMN_CONTAINER_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.HOME_SCREEN_LAZY_COLUMN_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.LOADER_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.USER_ITEM_TAG
import com.namle197.ui.UserItem

@Composable
internal fun HomeRoute(
    viewmodel: HomeScreenViewModel = hiltViewModel(),
    onItemClick: (loginUserName: String, avatarUrl: String) -> Unit
) {
    val uiState by viewmodel.homeScreenUiState.collectAsState()
    val loadMoreState by viewmodel.loadMoreState.collectAsState()
    val uiEvent by viewmodel.uiEvent.collectAsState(initial = "")
    HomeScreen(
        uiState = uiState,
        loadMoreState = loadMoreState,
        uiEvent = uiEvent,
        onItemClick = onItemClick,
        onLoadMore = viewmodel::loadMore,
        clearUiEvent = viewmodel::clearUiEvent
    )
}

@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    loadMoreState: Boolean,
    uiEvent: String,
    onItemClick: (loginUserName: String, avatarUrl: String) -> Unit,
    onLoadMore: () -> Unit,
    clearUiEvent: () -> Unit

) {
    val localContext = LocalContext.current

    LaunchedEffect(uiEvent) {
        if (uiEvent.isNotEmpty()) {
            Toast.makeText(localContext, uiEvent, Toast.LENGTH_SHORT).show()
            clearUiEvent()
        }
    }

    ScreenScaffold(topBarLabel = HOME_SCREEN_TOP_BAR_LABEL, onBackClick = {}) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag(COLUMN_CONTAINER_TAG)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (val result = uiState) {
                HomeScreenUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp).testTag(LOADER_TAG),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is HomeScreenUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .testTag(HOME_SCREEN_LAZY_COLUMN_TAG),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(result.users.size) { index ->
                            val user = result.users[index]
                            if (index >= result.users.size - 1 && !loadMoreState) {
                                onLoadMore()
                            }
                            UserItem(
                                name = user.login,
                                profileUrl = user.htmlUrl,
                                location = null,
                                avatarUrl = user.avatarUrl,
                                modifier = Modifier.padding(top = 8.dp).testTag(USER_ITEM_TAG),
                                onClick = { onItemClick(user.login, user.avatarUrl) }
                            )
                        }
                        item {
                            if (loadMoreState) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }

                HomeScreenUiState.Error -> {
                    // Show retry button
                }
            }
        }
    }
}