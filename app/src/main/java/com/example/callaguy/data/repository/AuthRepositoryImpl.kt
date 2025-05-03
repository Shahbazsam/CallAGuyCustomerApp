package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.LoginResponseDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.repository.AuthRepository
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApiService: ApiService
) : AuthRepository {
    override suspend fun register(
       data : AuthenticationRequestDto
    ): ResultClass<Unit> {
        return try {
            val response = authApiService.register(data)
            ResultClass.Authorized()
        }catch (e : HttpException) {
            when(e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        }catch (e : Exception) {
            ResultClass.UnKnownError()
        }
    }

    override suspend fun login(data: LoginRequestDto): LoginResponseDto {
        return authApiService.logIn(data)
    }
}