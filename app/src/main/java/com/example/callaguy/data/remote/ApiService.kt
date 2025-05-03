package com.example.callaguy.data.remote

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/customer_auth/register")
    suspend fun register( @Body data : AuthenticationRequestDto)

    @POST("/customer_auth/login")
    suspend fun logIn(@Body data : LoginRequestDto) : LoginResponseDto


}