package com.example.callaguy.data.dto.Authentication

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val email : String,
    val password : String
)
