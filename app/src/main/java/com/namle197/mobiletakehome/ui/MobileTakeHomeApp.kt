package com.namle197.mobiletakehome.ui

import androidx.compose.runtime.Composable
import com.namle197.mobiletakehome.navigation.MobileTakeHomeNavHost

@Composable
fun MobileTakeHomeApp(
    appState: MobileTakeHomeAppState = rememberMobileTakeHomeAppState()
) {
    MobileTakeHomeNavHost(appState = appState)
}