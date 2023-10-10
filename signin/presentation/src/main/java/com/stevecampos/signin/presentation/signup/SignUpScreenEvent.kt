package com.stevecampos.signin.presentation.signup

sealed class SignUpScreenEvent {
    data class OnPasswordChanged(val password: String): SignUpScreenEvent()
    object OnSignUpButtonClicked: SignUpScreenEvent()
}