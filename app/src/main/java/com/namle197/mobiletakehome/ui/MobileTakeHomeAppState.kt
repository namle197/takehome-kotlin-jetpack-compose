package com.namle197.mobiletakehome.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.namle197.home.navigation.navigateToHome
import com.namle197.mobiletakehome.navigation.TopLevelDestination

@Composable
fun rememberMobileTakeHomeAppState(
    navController: NavHostController = rememberNavController(),
): MobileTakeHomeAppState {
    return remember(navController) {
        MobileTakeHomeAppState(
            navController
        )
    }
}

@Stable
class MobileTakeHomeAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    /*val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            generateRoute -> GENERATE
            galleryRoute -> GALLERY
            else -> null
        }*/

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> {
                navController.navigateToHome(topLevelNavOptions)
            }
            TopLevelDestination.USER_DETAIL -> {
//                navController.navigateToUserDetail(topLevelNavOptions)
            }
        }
    }
}