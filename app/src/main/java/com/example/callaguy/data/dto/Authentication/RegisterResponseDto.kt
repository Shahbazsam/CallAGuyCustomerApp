package com.example.callaguy.data.dto.Authentication

import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponseDto(
    val status : String,
    val message : String
)
