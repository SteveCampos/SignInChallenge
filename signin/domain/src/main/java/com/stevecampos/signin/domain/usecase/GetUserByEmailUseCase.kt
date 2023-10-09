package com.stevecampos.signin.domain.usecase

import com.stevecampos.signin.domain.repository.SignInRepository

class GetUserByEmailUseCase(private val repository: SignInRepository) {

    suspend fun getUserByEmail(email: String) = repository.getUserByEmail(email)
}