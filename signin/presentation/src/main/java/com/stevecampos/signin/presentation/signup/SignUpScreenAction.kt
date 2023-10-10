package com.stevecampos.signin.presentation.signup

sealed class SignUpScreenAction {
    object NavigateToHome : SignUpScreenAction()
    object DisplayDefaultError : SignUpScreenAction()
}