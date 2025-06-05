package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.supportTicket.CreateSupportTicketDto
import com.example.callaguy.data.dto.supportTicket.SupportTicketsDto

interface SupportTicketRepository {
    suspend fun createTicket(data : CreateSupportTicketDto)
    suspend fun getSupportTickets() : List<SupportTicketsDto>
}