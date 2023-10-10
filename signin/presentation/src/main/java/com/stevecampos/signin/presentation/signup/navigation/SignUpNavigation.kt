package com.stevecampos.signin.presentation.signup.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.signin.presentation.signup.SignUpScreen
import com.stevecampos.signin.presentation.signup.SignUpViewModel

class SignUpNavigation {
    companion object {
        const val arg_email = "arg_email"
        const val arg_can_edit_email = "arg_email"
        internal const val route = "/signup?email={$arg_email}&edit={$arg_can_edit_email}"

        fun getRouteToNavigate(email: String, canEditEmail: Boolean): String =
            route.replace("{$arg_email}", email)
                .replace("{$arg_can_edit_email}", "$canEditEmail")
    }
}

fun NavGraphBuilder.signUpScreen(
    navigateToHome: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    onBackClicked: () -> Unit
) {
    composable(SignUpNavigation.route) {
        val viewModel = hiltViewModel<SignUpViewModel>()
        val state = viewModel.screenState.collectAsStateWithLifecycle().value
        SignUpScreen(
            state = state,
            handleEvent = viewModel::handleEvent,
            action = viewModel.action,
            navigateToHome = navigateToHome,
            navigateToForgotPassword = navigateToForgotPassword,
            onBackClicked = onBackClicked
        )
    }
}