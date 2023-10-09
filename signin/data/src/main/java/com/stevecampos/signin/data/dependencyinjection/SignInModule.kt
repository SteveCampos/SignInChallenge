package com.stevecampos.signin.data.dependencyinjection

import com.stevecampos.signin.data.repository.SignInApiService
import com.stevecampos.signin.data.repository.SignInRemote
import com.stevecampos.signin.domain.repository.SignInRepository
import com.stevecampos.signin.domain.usecase.GetUserByEmailUseCase
import com.stevecampos.signin.domain.usecase.RegisterUserUseCase
import com.stevecampos.signin.domain.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignInModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): SignInApiService =
        retrofit.create(SignInApiService::class.java)

    @Provides
    @Singleton
    fun provideSignInRepository(apiService: SignInApiService): SignInRepository =
        SignInRemote(apiService)

    @Provides
    @Singleton
    fun provideGetUserByEmailUseCase(repository: SignInRepository) =
        GetUserByEmailUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(repository: SignInRepository) =
        RegisterUserUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: SignInRepository) =
        SignInUseCase(repository)
}