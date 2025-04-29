package com.example.callaguy.domain.usecase

import android.provider.ContactsContract.RawContacts.Data
import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import com.example.callaguy.domain.repository.AuthRepository

class AuthUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun register(data : AuthenticationRequestDto ) : Result<RegisterResponseDto> {
        return try {
            val response = authRepository.register(data)
            if (response.isSuccessful){
                Result.success(response.body() ?: RegisterResponseDto(status = "OK" , message = "registration Successful"))
            } else {
                Result.failure(Exception("Registration Failed"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

}