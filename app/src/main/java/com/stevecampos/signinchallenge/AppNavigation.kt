package com.stevecampos.signinchallenge

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stevecampos.signin.presentation.welcome.navigation.WelcomeNavigation
import com.stevecampos.signin.presentation.welcome.navigation.welcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WelcomeNavigation.getRouteToNavigate()
    ) {
        welcomeScreen(
            navigateToLogin = {},
            navigateToSignUp = { _, _ -> },
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}