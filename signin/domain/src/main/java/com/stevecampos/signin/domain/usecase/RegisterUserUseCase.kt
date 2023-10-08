package com.stevecampos.signin.domain.usecase

import com.stevecampos.signin.domain.entities.RegisterUserRequest
import com.stevecampos.signin.domain.repository.SignInRepository

class RegisterUserUseCase(private val repository: SignInRepository) {

    suspend fun registerUser(request: RegisterUserRequest) = repository.registerUser(request)
}