package com.example.callaguy.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.RegisterResponseDto
import com.example.callaguy.domain.usecase.AuthUserUseCase
import com.example.callaguy.domain.validation.ValidateEmail
import com.example.callaguy.domain.validation.ValidatePassword
import com.example.callaguy.domain.validation.ValidateRepeatedPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail : ValidateEmail = ValidateEmail(),
    private val validatePassword : ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword : ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val registerUseCase : AuthUserUseCase
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val _registrationEventChannel = Channel<ValidationEvent>()
    val registrationEventChannel = _registrationEventChannel.receiveAsFlow()

    fun onEvent(event : RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.AddressChanged -> {
                state = state.copy(address = event.address)
            }
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.UserNameChanged -> {
                state = state.copy(userName = event.userName)
            }
            RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatPasswordResult = validateRepeatedPassword.execute(state.password , state.repeatedPassword)

        val hasError = listOf(
            emailResult, passwordResult, repeatPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatPasswordResult.errorMessage
            )
            return
        }

        if (!hasError) {
            viewModelScope.launch {
                _registrationEventChannel.send(ValidationEvent.Loading)
                val data = AuthenticationRequestDto(
                    userName = state.userName,
                    email = state.email,
                    password = state.password,
                    phone = state.phoneNumber,
                    address = state.address,
                )
                val result = registerUseCase.register(data)
                result.onSuccess {
                    _registrationEventChannel.send(ValidationEvent.Success(it))
                }.onFailure { error ->
                    _registrationEventChannel.send(ValidationEvent.Error(message = error.message ?: "UnknownError"))
                }

            }
        }
    }

    sealed class ValidationEvent {
        object Loading : ValidationEvent()
        data class Success(val result : RegisterResponseDto) : ValidationEvent()
        data class Error(val message : String) : ValidationEvent()
    }

}