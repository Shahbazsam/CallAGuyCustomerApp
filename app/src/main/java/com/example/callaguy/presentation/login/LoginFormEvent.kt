package com.example.callaguy.presentation.login

import com.example.callaguy.presentation.auth.RegistrationFormEvent

sealed class LoginFormEvent {
    data class EmailChanged(val email : String) : LoginFormEvent()
    data class PasswordChanged(val password : String) : LoginFormEvent()

    object Submit : LoginFormEvent()
}