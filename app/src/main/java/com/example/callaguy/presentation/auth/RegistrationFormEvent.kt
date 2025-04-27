package com.example.callaguy.presentation.auth

sealed class RegistrationFormEvent {
    data class UserNameChanged(val userName : String) : RegistrationFormEvent()
    data class EmailChanged(val email : String) : RegistrationFormEvent()
    data class PasswordChanged(val password : String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword : String) : RegistrationFormEvent()
    data class PhoneNumberChanged(val phoneNumber : String) : RegistrationFormEvent()
    data class AddressChanged(val address : String) : RegistrationFormEvent()

    object Submit : RegistrationFormEvent()
}