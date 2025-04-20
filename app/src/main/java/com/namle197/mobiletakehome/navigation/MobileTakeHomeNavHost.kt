package com.namle197.mobiletakehome.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.namle197.home.navigation.homeRoute
import com.namle197.home.navigation.homeScreen
import com.namle197.home.navigation.navigateToHome
import com.namle197.mobiletakehome.ui.MobileTakeHomeAppState
import com.namle197.userdetail.navigation.navigateToUserDetail
import com.namle197.userdetail.navigation.userDetailScreen

@Composable
fun MobileTakeHomeNavHost(
    appState: MobileTakeHomeAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {
    val navController = /*appState.navController*/ rememberNavController()
    val context  = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onItemClick = { loginUserName, avatarUrl ->
                navController.navigateToUserDetail(loginUserName, avatarUrl)
                Log.e("TEST1", "Item is clicked >>> $loginUserName")
            }
        )

        userDetailScreen(
            onBack = { navController.popBackStack() }
        )
    }
}