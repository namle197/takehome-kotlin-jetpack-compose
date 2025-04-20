package com.namle197.mobiletakehome.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.namle197.home.navigation.homeRoute
import com.namle197.home.navigation.homeScreen
import com.namle197.mobiletakehome.ui.MobileTakeHomeAppState

@Composable
fun MobileTakeHomeNavHost(
    appState: MobileTakeHomeAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {
    val navController = appState.navController
    val context  = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onItemClick = { id ->
//                navController.navigateToLoading(prompt, styleId)
                Log.e("TEST1", "Item is clicked >>> $id")
            }
        )
    }
}