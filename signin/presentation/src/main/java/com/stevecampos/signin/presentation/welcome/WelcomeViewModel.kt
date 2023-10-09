package com.stevecampos.signin.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.executeTask
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.domain.usecase.GetUserByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<WelcomeScreenAction>(replay = 0)
    val action: SharedFlow<WelcomeScreenAction> = _actionFlow

    private val _screenState: MutableStateFlow<WelcomeScreenState> = MutableStateFlow(
        WelcomeScreenState(email = "")
    )
    val screenState get() = _screenState.asStateFlow()

    private fun onSubmitEmail() {

        val email = _screenState.value.email

        executeTask(
            coroutineDispatcher = coroutineDispatcher,
            onSuccess = ::getUserByEmailSuccess,
            onFailure = ::getUserByEmailFailure
        ) {
            getUserByEmailUseCase.getUserByEmail(email)
        }
    }


    private fun getUserByEmailSuccess(user: User) {
        sendAction(action = WelcomeScreenAction.NavigateToLogin(user))
    }

    private fun getUserByEmailFailure(failure: Failure) {
        if (failure is Failure.NotFound) {
            onUserNotFound()
            return
        }
        sendAction(action = WelcomeScreenAction.DisplayDefaultMessage)
    }

    private fun onUserNotFound() {
        val email = _screenState.value.email
        sendAction(
            action = WelcomeScreenAction.NavigateToSignUp(
                email = email,
                canEditEmail = false
            )
        )
    }

    fun handleEvent(event: WelcomeScreenEvent) {
        when (event) {
            is WelcomeScreenEvent.OnEmailChanged -> onEmailChanged(event)
            is WelcomeScreenEvent.OnSubmitEmailButtonClicked -> onSubmitEmail()
            is WelcomeScreenEvent.OnSignUpLinkClicked -> onSignUpLinkClicked()
            else -> {}
        }
    }

    private fun onEmailChanged(event: WelcomeScreenEvent.OnEmailChanged) {
        _screenState.value = _screenState.value.copy(email = event.email)
    }

    private fun onSignUpLinkClicked() {
        val email = _screenState.value.email
        sendAction(WelcomeScreenAction.NavigateToSignUp(email, canEditEmail = true))
    }

    private fun sendAction(action: WelcomeScreenAction) {
        viewModelScope.launch {
            _actionFlow.emit(action)
        }
    }
}