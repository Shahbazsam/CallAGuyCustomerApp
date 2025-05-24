package com.example.callaguy.domain.validation

data class ValidationResult(
    val successful : Boolean,
    val errorMessage : String? = null
)


