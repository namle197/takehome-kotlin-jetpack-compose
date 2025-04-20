package com.namle197.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.namle197.common.stringconstant.StringConstants.HOME_SCREEN_TOP_BAR_LABEL
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

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        HOME_SCREEN_TOP_BAR_LABEL,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val result = uiState) {
                HomeScreenUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is HomeScreenUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
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
                                modifier = Modifier.padding(top = 8.dp),
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