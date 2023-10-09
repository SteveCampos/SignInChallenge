package com.stevecampos.signin.presentation.login

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun LoginScreen(
    state: LoginScreenState,
    handleEvent: (LoginScreenEvent) -> Unit,
    actions: SharedFlow<LoginScreenAction>,
    navigateToHome: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    onBackClicked: () -> Unit
) {
    Text("LoginScreen")
}