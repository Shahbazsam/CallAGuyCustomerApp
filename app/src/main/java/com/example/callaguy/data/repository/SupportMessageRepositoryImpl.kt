package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.chatScreen.SendSupportMessageDto
import com.example.callaguy.data.dto.chatScreen.SupportMessagesDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.SupportMessageRepository

class SupportMessageRepositoryImpl(
    private val apiService: ApiService
) : SupportMessageRepository {

    override suspend fun getSupportMessages( id : Int): List<SupportMessagesDto> {
        return apiService.getSupportMessages(id)
    }

    override suspend fun sendMessage(data: SendSupportMessageDto) {
        apiService.sendSupportMessage(data)
    }
}