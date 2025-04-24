package com.namle197.userdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.namle197.userdetail.UserDetailRoute

const val userDetailRoute = "user_detail_route"

// Extension function to navigate to the user detail screen
fun NavController.navigateToUserDetail(loginUserName: String, avatarUrl: String, navOptions: NavOptions? = null) {
    this.navigate("$userDetailRoute?loginUserName=$loginUserName&avatarUrl=$avatarUrl", navOptions)
}

// Extension function to add the user detail screen to the navigation graph
fun NavGraphBuilder.userDetailScreen(
    onBack: () -> Unit
) {
    composable(
        route = "$userDetailRoute?loginUserName={loginUserName}&avatarUrl={avatarUrl}",
        arguments = listOf(
            navArgument("loginUserName") { type = NavType.StringType },
            navArgument("avatarUrl") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val loginUserName = backStackEntry.arguments?.getString("loginUserName")
        val avatarUrl = backStackEntry.arguments?.getString("avatarUrl")
        UserDetailRoute(onBack = onBack, loginUserName = loginUserName, avatarUrl = avatarUrl)

    }
}