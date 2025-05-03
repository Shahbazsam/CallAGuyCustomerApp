package com.example.callaguy.domain.usecase

import android.content.SharedPreferences
import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.repository.AuthRepository
import retrofit2.HttpException
import androidx.core.content.edit

class AuthUserUseCase(
    private val authRepository: AuthRepository,
    private val preference : SharedPreferences
) {
    suspend fun register(data : AuthenticationRequestDto ) : ResultClass<Unit> {
        return  authRepository.register(data)
    }

    suspend fun login(data : LoginRequestDto) :ResultClass<Unit> {
        return try {
            val response = authRepository.login(data)
            preference.edit() { putString("jwt", response.token) }
            ResultClass.Authorized()
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
}