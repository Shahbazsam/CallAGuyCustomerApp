package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.chatScreen.SendSupportMessageDto
import com.example.callaguy.data.dto.chatScreen.SupportMessagesDto

interface SupportMessageRepository {
    suspend fun sendMessage(data : SendSupportMessageDto)

    suspend fun getSupportMessages( id : Int ) : List<SupportMessagesDto>
}