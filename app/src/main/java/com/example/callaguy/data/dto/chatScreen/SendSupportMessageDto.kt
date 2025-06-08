package com.example.callaguy.data.dto.chatScreen

import kotlinx.serialization.Serializable


@Serializable
data class SendSupportMessageDto(
    val ticketId : Int ,
    val message : String
)
