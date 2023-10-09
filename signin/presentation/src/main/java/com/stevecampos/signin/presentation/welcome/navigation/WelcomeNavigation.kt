package com.stevecampos.signin.presentation.welcome.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.presentation.welcome.WelcomeScreen
import com.stevecampos.signin.presentation.welcome.WelcomeScreenState
import kotlinx.coroutines.flow.MutableSharedFlow

class WelcomeNavigation {
    companion object {
        internal const val route = "/welcome"
        fun getRouteToNavigate() = route
    }
}

fun NavGraphBuilder.welcomeScreen(
    navigateToLogin: (User) -> Unit,
    navigateToSignUp: (String, Boolean) -> Unit,
    onBackPressed: () -> Unit
) {
    composable(WelcomeNavigation.route) {
        WelcomeScreen(
            state = WelcomeScreenState(email = "steve.campos@email.com"),
            handleEvent = {},
            actions = MutableSharedFlow(replay = 0),
            navigateToLogin = navigateToLogin,
            navigateToSignUp = navigateToSignUp,
            onBackPressed = onBackPressed
        )
    }
}