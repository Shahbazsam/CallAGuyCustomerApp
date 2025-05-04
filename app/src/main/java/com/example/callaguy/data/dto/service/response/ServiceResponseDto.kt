package com.example.callaguy.data.dto.service.response

import kotlinx.serialization.Serializable


@Serializable
data class ServiceResponseDto(
    val id : Int ,
    val name : String ,
    val description : String ,
    val imageUrl : String
)
