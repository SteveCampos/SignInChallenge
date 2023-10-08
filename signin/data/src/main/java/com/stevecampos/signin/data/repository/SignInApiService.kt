package com.stevecampos.signin.data.repository

import com.stevecampos.signin.domain.entities.RegisterUserRequest
import com.stevecampos.signin.domain.entities.RegisterUserResponse
import com.stevecampos.signin.domain.entities.SignInRequest
import com.stevecampos.signin.domain.entities.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApiService {

    @POST("/api/login")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("/api/register")
    suspend fun registerUser(@Body request: RegisterUserRequest): Response<RegisterUserResponse>
}