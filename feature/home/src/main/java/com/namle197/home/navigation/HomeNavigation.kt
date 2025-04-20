package com.namle197.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.namle197.home.HomeRoute

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onItemClick: (loginUserName: String, avatarUrl: String) -> Unit
) {
    composable(route = homeRoute) {
        HomeRoute(onItemClick = onItemClick)
    }
}