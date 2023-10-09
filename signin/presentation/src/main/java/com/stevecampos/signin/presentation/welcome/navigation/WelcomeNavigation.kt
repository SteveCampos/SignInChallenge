package com.stevecampos.signin.presentation.welcome.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.presentation.welcome.WelcomeScreen
import com.stevecampos.signin.presentation.welcome.WelcomeViewModel

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

        val viewModel = hiltViewModel<WelcomeViewModel>()
        val state = viewModel.screenState.collectAsStateWithLifecycle().value
        WelcomeScreen(
            state = state,
            handleEvent = viewModel::handleEvent,
            actions = viewModel.action,
            navigateToLogin = navigateToLogin,
            navigateToSignUp = navigateToSignUp,
            onBackPressed = onBackPressed
        )
    }
}