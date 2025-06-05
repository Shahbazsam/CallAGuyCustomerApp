package com.example.callaguy.data.dto.supportTicket

import kotlinx.serialization.Serializable


@Serializable
data class CreateSupportTicketDto(
    val serviceRequestId : Int,
    val issueType : String
)
