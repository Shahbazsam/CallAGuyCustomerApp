package com.example.callaguy.domain.model

import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import java.time.LocalDateTime

data class SupportTicketsModel(
    val ticketId : Int,
    val customerId : Int,
    val serviceRequestId : Int,
    val issueType : String,
    val status : SupportTicketStatus,
    val createdAt : LocalDateTime
)
