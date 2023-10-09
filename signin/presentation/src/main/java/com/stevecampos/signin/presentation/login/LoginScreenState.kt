package com.stevecampos.signin.presentation.login

import com.stevecampos.signin.domain.entities.User

data class LoginScreenState(val user: User, val password: String) {
    fun passwordMeetRequirements() = password.length >= 6
}