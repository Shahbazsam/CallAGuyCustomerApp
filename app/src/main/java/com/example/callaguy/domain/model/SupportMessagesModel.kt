package com.example.callaguy.domain.model

import com.example.callaguy.data.dto.chatScreen.SupportMessageSender
import java.time.LocalDateTime

data class SupportMessagesModel(
    val ticketId : Int,
    val sender : SupportMessageSender,
    val message : String,
    val createdAt : LocalDateTime
)
