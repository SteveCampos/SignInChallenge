package com.stevecampos.signin.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.domain.usecase.SignInUseCase
import com.stevecampos.signin.presentation.login.navigation.LoginNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userEmail: String =
        checkNotNull(savedStateHandle.get<String>(LoginNavigation.arg_user_email))
    private val userNames: String =
        checkNotNull(savedStateHandle.get<String>(LoginNavigation.arg_user_names))
    private val userPhotoUrl: String =
        checkNotNull(savedStateHandle.get<String>(LoginNavigation.arg_user_photo_url))

    private val _actionFlow = MutableSharedFlow<LoginScreenAction>(replay = 0)
    val action: SharedFlow<LoginScreenAction> = _actionFlow

    private val _screenState: MutableStateFlow<LoginScreenState> = MutableStateFlow(
        LoginScreenState(
            user = User(email = userEmail, names = userNames, photoUrl = userPhotoUrl),
            password = ""
        )
    )
    val screenState get() = _screenState.asStateFlow()

    fun handleEvent(loginScreenEvent: LoginScreenEvent) {

    }
}