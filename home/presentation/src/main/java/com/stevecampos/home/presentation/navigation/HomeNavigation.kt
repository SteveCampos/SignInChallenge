package com.stevecampos.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.home.presentation.HomeScreen

class HomeNavigation {
    companion object {
        internal const val route = "/home"

        fun getRouteToNavigate() = route
    }
}

fun NavGraphBuilder.homeScreen() {
    composable(HomeNavigation.route) {
        HomeScreen()
    }
}