package com.stevecampos.signin.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.stevecampos.signin.presentation.signup.navigation.SignUpNavigation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val email: String =
        checkNotNull(savedStateHandle.get<String>(SignUpNavigation.arg_email))

    private val _actionFlow = MutableSharedFlow<SignUpScreenAction>(replay = 0)
    val action: SharedFlow<SignUpScreenAction> = _actionFlow

    private val _screenState: MutableStateFlow<SignUpScreenState> = MutableStateFlow(
        SignUpScreenState(initialEmail = email, currentEmail = "", password = "")
    )
    val screenState get() = _screenState.asStateFlow()

    fun handleEvent(event: SignUpScreenEvent) {

    }
}