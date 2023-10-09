package com.stevecampos.signinchallenge

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stevecampos.signin.presentation.login.navigation.LoginNavigation
import com.stevecampos.signin.presentation.login.navigation.loginScreen
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
            navigateToLogin = { user ->
                val route = LoginNavigation.getRouteToNavigate(user)
                navController.navigate(route)
            },
            navigateToSignUp = { _, _ -> },
            onBackPressed = {
                navController.popBackStack()
            }
        )
        loginScreen(
            navigateToHome = {},
            navigateToForgotPassword = {},
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }
}