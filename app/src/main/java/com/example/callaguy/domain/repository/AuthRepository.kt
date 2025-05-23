package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.LoginResponseDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import com.example.callaguy.domain.model.ResultClass
import retrofit2.Response

interface AuthRepository {

    suspend fun register( data : AuthenticationRequestDto) : ResultClass<Unit>

    suspend fun login(data : LoginRequestDto) : LoginResponseDto
}