package com.namle197.designsystem.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import com.namle197.common.stringconstant.StringConstants.HOME_SCREEN_TOP_BAR_LABEL
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_BACK_BUTTON_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TAG
import com.namle197.testing.androidtesttag.AndroidTestTag.TOP_APP_BAR_TEXT_TAG

// Scaffold for the screen (use in both Home and Detail screens)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScaffold(topBarLabel: String,onBackClick: () -> Unit, component: @Composable (paddingValues: PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.testTag(TOP_APP_BAR_TAG),
                title = {
                    Text(
                        topBarLabel,
                        modifier = Modifier.testTag(TOP_APP_BAR_TEXT_TAG),
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
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = TOP_APP_BAR_BACK_BUTTON_TAG
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        component(paddingValues)
    }
}