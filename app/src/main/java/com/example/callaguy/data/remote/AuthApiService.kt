package com.example.callaguy.data.remote

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/customer_auth/register")
    suspend fun register( @Body data : AuthenticationRequestDto) : Response<RegisterResponseDto>
}