package com.example.callaguy.domain.model

import com.example.callaguy.data.dto.chatScreen.SendSupportMessageDto

data class SendSupportMessageModel(
    val ticketId : Int,
    val message : String
){
    fun toDto() : SendSupportMessageDto {
        return SendSupportMessageDto(
            ticketId = ticketId,
            message = message
        )
    }
}
