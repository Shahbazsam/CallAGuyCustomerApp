package com.example.callaguy.domain.validation

class ValidateRepeatedPassword {
    fun execute(password : String , repeatedPassword :String) : ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Passwords doesn't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}