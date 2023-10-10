package com.stevecampos.signin.presentation.signup

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SignUpScreen(
    state: SignUpScreenState,
    handleEvent: (SignUpScreenEvent) -> Unit,
    action: SharedFlow<SignUpScreenAction>,
    navigateToHome: () -> Unit,
    navigateToForgotPassword: ()-> Unit,
    onBackClicked: () -> Unit,
) {

}