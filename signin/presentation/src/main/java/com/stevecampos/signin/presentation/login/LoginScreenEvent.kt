package com.stevecampos.signin.presentation.login

sealed class LoginScreenEvent {
    data class OnPasswordChanged(val password: String) : LoginScreenEvent()
    object OnLoginButtonClicked : LoginScreenEvent()
    object OnForgotPasswordLinkClicked: LoginScreenEvent()
}