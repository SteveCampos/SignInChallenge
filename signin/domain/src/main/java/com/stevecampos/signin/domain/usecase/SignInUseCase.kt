package com.stevecampos.signin.domain.usecase

import com.stevecampos.signin.domain.entities.SignInRequest
import com.stevecampos.signin.domain.repository.SignInRepository

class SignInUseCase(private val repository: SignInRepository) {

    suspend fun signIn(request: SignInRequest) = repository.signIn(request)
}