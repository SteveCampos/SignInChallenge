package com.stevecampos.signin.presentation.login.navigation

import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.presentation.login.LoginScreen
import com.stevecampos.signin.presentation.login.LoginViewModel

class LoginNavigation {
    companion object {
        internal const val arg_user_email = "arg_user_email"
        internal const val arg_user_names = "arg_user_names"
        internal const val arg_user_photo_url = "arg_user_photo_url"

        internal const val route =
            "/login?email={$arg_user_email}&names={$arg_user_names}&photo={$arg_user_photo_url}"

        fun getRouteToNavigate(user: User): String = route
            .replace("{$arg_user_email}", user.email)
            .replace("{$arg_user_names}", user.names)
            .replace("{$arg_user_photo_url}", Uri.encode(user.photoUrl))
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit, navigateToForgotPassword: () -> Unit, onBackClicked: () -> Unit
) {
    composable(LoginNavigation.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val state = viewModel.screenState.collectAsStateWithLifecycle().value
        LoginScreen(
            state = state,
            handleEvent = viewModel::handleEvent,
            actions = viewModel.action,
            navigateToHome = navigateToHome,
            navigateToForgotPassword = navigateToForgotPassword,
            onBackClicked = onBackClicked
        )
    }
}