package com.example.callaguy.data.repository

import android.util.Log
import com.example.callaguy.data.dto.supportTicket.CreateSupportTicketDto
import com.example.callaguy.data.dto.supportTicket.SupportTicketsDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.SupportTicketRepository

class SupportTicketRepositoryImpl(
    private val apiService: ApiService
) : SupportTicketRepository {

    override suspend fun createTicket(data: CreateSupportTicketDto) {
        apiService.createSupportTicket(data = data)
    }

    override suspend fun getSupportTickets(): List<SupportTicketsDto> {
        Log.d("response" , "${apiService.getSupportTickets()}")
        return apiService.getSupportTickets()
    }
}