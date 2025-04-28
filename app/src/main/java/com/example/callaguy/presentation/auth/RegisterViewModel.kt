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

    private var _state by mutableStateOf(RegistrationFormState())
    val state = _state

    private val _registrationEventChannel = Channel<ValidationEvent>()
    val registrationEventChannel = _registrationEventChannel.receiveAsFlow()

    fun onEvent(event : RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.AddressChanged -> {
                _state = _state.copy(address = event.address)
            }
            is RegistrationFormEvent.EmailChanged -> {
                _state = _state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                _state = _state.copy(password = event.password)
            }
            is RegistrationFormEvent.PhoneNumberChanged -> {
                _state = _state.copy(phoneNumber = event.phoneNumber)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                _state = _state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.UserNameChanged -> {
                _state = _state.copy(userName = event.userName)
            }
            RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(_state.email)
        val passwordResult = validatePassword.execute(_state.password)
        val repeatPasswordResult = validateRepeatedPassword.execute(_state.password , state.repeatedPassword)

        val hasError = listOf(
            emailResult, passwordResult, repeatPasswordResult
        ).any { !it.successful }

        if (hasError) {
            _state = _state.copy(
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