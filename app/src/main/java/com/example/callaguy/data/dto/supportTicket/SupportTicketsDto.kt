package com.example.callaguy.data.dto.supportTicket

import com.example.callaguy.domain.model.SupportTicketsModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class SupportTicketsDto(
    val ticketId : Int,
    val customerId : Int,
    val serviceRequestId : Int,
    val issueType : String,
    val status : SupportTicketStatus,
    @Contextual
    val createdAt : LocalDateTime
){
    fun toModel() : SupportTicketsModel {
        return SupportTicketsModel(
            ticketId = ticketId,
            customerId = customerId,
            serviceRequestId = serviceRequestId,
            issueType = issueType,
            status = status,
            createdAt = createdAt
        )
    }
}

enum class SupportTicketStatus {
    OPEN,
    RESOLVED
}