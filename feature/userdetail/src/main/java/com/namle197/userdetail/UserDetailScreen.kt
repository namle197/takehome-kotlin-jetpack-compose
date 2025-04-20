package com.namle197.userdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Group
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.namle197.common.converter.formatFollower
import com.namle197.common.stringconstant.StringConstants.API_ERROR
import com.namle197.common.stringconstant.StringConstants.NO_INFORMATION
import com.namle197.common.stringconstant.StringConstants.USER_DETAIL_BLOG_LABEL
import com.namle197.common.stringconstant.StringConstants.USER_DETAIL_TOP_BAR_LABEL
import com.namle197.ui.BadgeItem
import com.namle197.ui.UserItem

@Composable
internal fun UserDetailRoute(
    viewModel: UserDetailScreenViewModel = hiltViewModel(),
    onBack: () -> Unit,
    loginUserName: String?,
    avatarUrl: String?
) {
    val uiState = viewModel.userDetailUiState.collectAsState()
    UserDetailScreen(
        uiState = uiState,
        onBack = onBack,
        loginUserName = loginUserName,
        avatarUrl = avatarUrl
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserDetailScreen(
    uiState: State<UserDetailUiState>,
    onBack: () -> Unit,
    loginUserName: String?,
    avatarUrl: String?
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        USER_DETAIL_TOP_BAR_LABEL,
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
                    IconButton(onClick = { onBack() }) {
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
                .padding(vertical = paddingValues.calculateTopPadding(), horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (val result = uiState.value) {
                UserDetailUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }

                is UserDetailUiState.Success -> {
                    val followerNumber = result.userDetail?.followers?.formatFollower() ?: API_ERROR
                    val followingNumber =
                        result.userDetail?.following?.formatFollower() ?: API_ERROR

                    UserItem(
                        name = loginUserName ?: "",
                        profileUrl = null,
                        location = result.userDetail?.location ?: NO_INFORMATION,
                        avatarUrl = avatarUrl ?: "",
                        modifier = Modifier.padding(top = 8.dp)
                    ) { }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgeItem(
                            icon = Icons.Default.Group,
                            count = followerNumber,
                            label = "Followers"
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                        BadgeItem(
                            icon = Icons.Default.BookmarkAdded,
                            count = followingNumber,
                            label = "Following"
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                USER_DETAIL_BLOG_LABEL,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                result.userDetail?.htmlUrl ?: API_ERROR,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

        }
    }
}