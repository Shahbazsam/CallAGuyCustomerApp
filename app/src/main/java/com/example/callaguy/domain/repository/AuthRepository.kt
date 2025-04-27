package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import retrofit2.Response

interface AuthRepository {

    suspend fun register( data : AuthenticationRequestDto) : Response<RegisterResponseDto>
}