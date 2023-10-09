package com.stevecampos.signin.presentation.welcome

import com.stevecampos.signin.domain.entities.User

sealed class WelcomeScreenAction {
    data class NavigateToSignUp(val email: String, val canEditEmail: Boolean) :
        WelcomeScreenAction()

    data class NavigateToLogin(val user: User) : WelcomeScreenAction()

    data class DisplayMessage(val message: String) : WelcomeScreenAction()
}