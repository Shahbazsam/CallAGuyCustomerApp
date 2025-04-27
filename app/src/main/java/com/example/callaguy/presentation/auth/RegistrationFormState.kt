package com.example.callaguy.presentation.auth

data class RegistrationFormState(
    val userName : String = "",
    val email : String = "",
    val emailError : String ? = null,
    val password :String = "",
    val passwordError: String? = null,
    val repeatedPassword : String = "" ,
    val repeatedPasswordError: String ? = null,
    val phoneNumber : String = "",
    val address  : String = ""
)
