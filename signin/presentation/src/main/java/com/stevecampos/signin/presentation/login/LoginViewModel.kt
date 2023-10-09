package com.stevecampos.signin.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.executeTask
import com.stevecampos.signin.domain.entities.SignInRequest
import com.stevecampos.signin.domain.entities.SignInResponse
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.domain.usecase.SignInUseCase
import com.stevecampos.signin.presentation.login.navigation.LoginNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val signInUseCase: SignInUseCase,
    savedStateHandle: SavedStateHandle
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


    private fun onLoginButtonClicked() {
        executeTask(
            coroutineDispatcher = coroutineDispatcher,
            onSuccess = ::onLoginSuccess,
            onFailure = ::onLoginFailure
        ) {

            val request = SignInRequest(email = userEmail, password = _screenState.value.password)
            signInUseCase.signIn(request)
        }
    }


    private fun onLoginSuccess(response: SignInResponse) {
        //save token
        sendAction(LoginScreenAction.NavigateToHome)
    }

    private fun onLoginFailure(failure: Failure) {
        sendAction(action = LoginScreenAction.DisplayErrorMessage)
    }

    private fun sendAction(action: LoginScreenAction) {
        viewModelScope.launch {
            _actionFlow.emit(action)
        }
    }

    fun handleEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            is LoginScreenEvent.OnLoginButtonClicked -> onLoginButtonClicked()
            else -> {}
        }
    }

    private fun onPasswordChanged(password: String) {
        _screenState.value = _screenState.value.copy(password = password)
    }
}