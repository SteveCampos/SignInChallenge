package com.stevecampos.signin.presentation.signup

sealed class SignUpScreenEvent {
    object OnPasswordChanged: SignUpScreenEvent()
    object OnSignUpButtonClicked: SignUpScreenEvent()
}