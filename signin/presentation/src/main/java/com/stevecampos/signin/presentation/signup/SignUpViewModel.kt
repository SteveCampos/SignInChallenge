package com.stevecampos.signin.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.executeTask
import com.stevecampos.signin.domain.entities.RegisterUserRequest
import com.stevecampos.signin.domain.entities.RegisterUserResponse
import com.stevecampos.signin.domain.usecase.RegisterUserUseCase
import com.stevecampos.signin.presentation.signup.navigation.SignUpNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val email: String =
        checkNotNull(savedStateHandle.get<String>(SignUpNavigation.arg_email))

    private val _actionFlow = MutableSharedFlow<SignUpScreenAction>(replay = 0)
    val action: SharedFlow<SignUpScreenAction> = _actionFlow

    private val _screenState: MutableStateFlow<SignUpScreenState> = MutableStateFlow(
        SignUpScreenState(initialEmail = email, currentEmail = "", password = "")
    )
    val screenState get() = _screenState.asStateFlow()

    private fun signUp() {
        executeTask(
            coroutineDispatcher = coroutineDispatcher,
            onSuccess = ::onSignUpSuccess,
            onFailure = ::onSignUpFailure
        ) {
            val request = RegisterUserRequest(email = email, password = _screenState.value.password)
            registerUserUseCase.registerUser(request)
        }
    }

    private fun onSignUpFailure(failure: Failure) {
        sendAction(action = SignUpScreenAction.DisplayDefaultError)
    }

    private fun onSignUpSuccess(response: RegisterUserResponse) {
        //save token here
        sendAction(action = SignUpScreenAction.NavigateToHome)
    }

    fun handleEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            is SignUpScreenEvent.OnSignUpButtonClicked -> signUp()
        }
    }

    private fun onPasswordChanged(password: String) {
        _screenState.value = _screenState.value.copy(password = password)
    }

    private fun sendAction(action: SignUpScreenAction) {
        viewModelScope.launch {
            _actionFlow.emit(action)
        }
    }
}