package com.stevecampos.signin.presentation.signup

data class SignUpScreenState(
    val initialEmail: String,
    val currentEmail: String,
    val password: String
){
    fun passwordMeetRequirements() = password.length >= 6
}