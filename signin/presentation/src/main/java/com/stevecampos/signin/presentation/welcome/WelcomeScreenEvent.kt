package com.stevecampos.signin.presentation.welcome

sealed interface WelcomeScreenEvent {
    data class OnEmailChanged(val email: String) : WelcomeScreenEvent

    object OnSubmitEmailButtonClicked : WelcomeScreenEvent

    object OnFacebookButtonClicked : WelcomeScreenEvent
    object OnGoogleButtonClicked : WelcomeScreenEvent
    object OnAppleButtonClicked : WelcomeScreenEvent

    object OnSignUpLinkClicked : WelcomeScreenEvent

    object OnForgetPasswordLinkClicked : WelcomeScreenEvent
}