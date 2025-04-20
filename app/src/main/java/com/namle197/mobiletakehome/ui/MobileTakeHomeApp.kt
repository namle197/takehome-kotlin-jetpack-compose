package com.namle197.mobiletakehome.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.namle197.mobiletakehome.navigation.MobileTakeHomeNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileTakeHomeApp(
    appState: MobileTakeHomeAppState = rememberMobileTakeHomeAppState()
) {
    /*Scaffold(
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        Column(Modifier.fillMaxSize().padding(paddingValues)) {
            MobileTakeHomeNavHost(appState = appState)
        }
    }*/
    MobileTakeHomeNavHost(appState = appState)
}