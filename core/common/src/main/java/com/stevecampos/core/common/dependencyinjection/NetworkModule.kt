package com.stevecampos.core.common.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient
                    .Builder()
                    .build()
            )
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Named("baseUrl")
    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://https://reqres.in/"
}