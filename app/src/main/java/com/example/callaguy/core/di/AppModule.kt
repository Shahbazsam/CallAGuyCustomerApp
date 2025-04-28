package com.example.callaguy.core.di

import com.example.callaguy.core.constants.Constants
import com.example.callaguy.data.remote.AuthApiService
import com.example.callaguy.data.repository.AuthRepositoryImpl
import com.example.callaguy.domain.repository.AuthRepository
import com.example.callaguy.domain.usecase.AuthUserUseCase
import com.example.callaguy.domain.validation.ValidateEmail
import com.example.callaguy.domain.validation.ValidatePassword
import com.example.callaguy.domain.validation.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() : OkHttpClient {
        return Constants.client
    }

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun ProvideAuthApiService(retrofit: Retrofit) : AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService) : AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository) : AuthUserUseCase {
        return AuthUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideEmailValidationUseCase() : ValidateEmail {
        return ValidateEmail()
    }
    @Provides
    @Singleton
    fun providePasswordValidationUseCase() : ValidatePassword {
        return ValidatePassword()
    }
    @Provides
    @Singleton
    fun provideRepeatPasswordValidationUseCase() : ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }


}