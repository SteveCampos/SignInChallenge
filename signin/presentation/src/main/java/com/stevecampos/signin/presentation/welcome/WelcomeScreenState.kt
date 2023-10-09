package com.stevecampos.signin.presentation.welcome

import android.util.Patterns

data class WelcomeScreenState(val email: String) {
    fun emailMeetRequirements() = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}