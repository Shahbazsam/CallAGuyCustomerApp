package com.example.callaguy.data.dto.Authentication

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDto(
    val token : String
)
