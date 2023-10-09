package com.stevecampos.signin.presentation.login

sealed class LoginScreenAction {
    object NavigateToHome : LoginScreenAction()
    object NavigateToForgotPassword : LoginScreenAction()
    object DisplayErrorMessage : LoginScreenAction()
}