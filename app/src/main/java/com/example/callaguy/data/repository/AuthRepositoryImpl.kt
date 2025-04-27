package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import com.example.callaguy.data.remote.AuthApiService
import com.example.callaguy.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(
       data : AuthenticationRequestDto
    ): Response<RegisterResponseDto> {
        return authApiService.register(data)
    }
}