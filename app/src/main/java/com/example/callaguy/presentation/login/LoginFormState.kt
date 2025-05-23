package com.example.callaguy.presentation.login

data class LoginFormState(
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val isLoading : Boolean = false
)