package com.example.callaguy.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.usecase.AuthUserUseCase
import com.example.callaguy.domain.validation.ValidateEmail
import com.example.callaguy.domain.validation.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val loginUseCase: AuthUserUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val _loginChannel = Channel<ResultClass<Unit>>()
    val loginChannel = _loginChannel.receiveAsFlow()

    fun onEvent( event : LoginFormEvent) {
        when(event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email
                )
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password
                )
            }
            LoginFormEvent.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult , passwordResult
        ).any{!it.successful}

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        if (!hasError) {
            viewModelScope.launch {
                state = state.copy(isLoading = true)

                val data = LoginRequestDto(
                    email = state.email,
                    password = state.password
                )
                val result  = loginUseCase.login(data)
                _loginChannel.send(result)
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }
}