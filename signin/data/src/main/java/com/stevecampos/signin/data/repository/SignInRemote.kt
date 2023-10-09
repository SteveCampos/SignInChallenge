package com.stevecampos.signin.data.repository

import com.stevecampos.core.common.Failure
import com.stevecampos.signin.domain.entities.RegisterUserRequest
import com.stevecampos.signin.domain.entities.SignInRequest
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.domain.repository.SignInRepository
import com.stevecampos.core.common.Result
import com.stevecampos.core.common.executeApiService

class SignInRemote(private val api: SignInApiService) : SignInRepository {

    override suspend fun registerUser(request: RegisterUserRequest) =
        executeApiService { api.registerUser(request) }

    override suspend fun signIn(request: SignInRequest) = executeApiService { api.signIn(request) }

    override suspend fun getUserByEmail(email: String): Result<User> {

        // There is no api on reqres.in that returns a user by his email (required to do the sign-in challenge) mocking this behavior
        val isReqResEmail = email.contains("@reqres.in")
        if (isReqResEmail) return Result.Success(
            User(
                email = email,
                names = "Steve Campos",
                photoUrl = "https://media.licdn.com/dms/image/C4E03AQGqvLLdQwaT6w/profile-displayphoto-shrink_200_200/0/1580079394722?e=1702512000&v=beta&t=H_wK-SdyrvZmRaJkkqIyHjDykLZOhBEUM18oOMPOpZs"
            )
        )
        return Result.Error(Failure.NotFound())
    }
}