package com.example.callaguy.domain.validation

import android.util.Patterns

class ValidateEmail {

    fun execute(email : String) : ValidationResult {
        if (email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "email can't be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "Not a valid email"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }
}