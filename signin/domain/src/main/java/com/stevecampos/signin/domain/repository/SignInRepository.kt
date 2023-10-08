package com.stevecampos.signin.domain.repository

import com.stevecampos.core.common.Result
import com.stevecampos.signin.domain.entities.RegisterUserRequest
import com.stevecampos.signin.domain.entities.RegisterUserResponse
import com.stevecampos.signin.domain.entities.SignInRequest
import com.stevecampos.signin.domain.entities.SignInResponse
import com.stevecampos.signin.domain.entities.User

interface SignInRepository {
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun registerUser(request: RegisterUserRequest): Result<RegisterUserResponse>
    suspend fun signIn(request: SignInRequest): Result<SignInResponse>
}