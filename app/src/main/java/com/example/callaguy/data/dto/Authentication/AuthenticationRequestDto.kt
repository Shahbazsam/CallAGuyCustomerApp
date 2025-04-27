package com.example.callaguy.data.dto.Authentication

import kotlinx.serialization.Serializable


@Serializable
data class AuthenticationRequestDto(
    val userName : String ,
    val email : String ,
    val password : String ,
    val phone : String ,
    val address : String
)
