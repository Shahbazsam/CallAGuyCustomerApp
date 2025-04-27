package com.example.callaguy.domain.validation

class ValidatePassword {

    fun execute(password :String) : ValidationResult {
        if (password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "Password length shouldn't be less than 8"
            )
        }
        val containsDigitsAndLetters = password.any{it.isDigit()} &&
                password.any { it.isLetter() }
        if (!containsDigitsAndLetters) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password should contain both letter and digit"
            )
        }
        return ValidationResult(
            successful = true
        )
     }
}