package com.example.callaguy.data.dto.chatScreen

import com.example.callaguy.domain.model.SupportMessagesModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class SupportMessagesDto(
    val ticketId : Int ,
    val sender : SupportMessageSender,
    val message : String,
    @Contextual
    val createdAt : LocalDateTime
){
    fun toModel() : SupportMessagesModel {
        return SupportMessagesModel(
            ticketId = ticketId,
            sender = sender,
            message = message,
            createdAt = createdAt
        )
    }
}

enum class SupportMessageSender {
    CUSTOMER,
    ADMIN
}