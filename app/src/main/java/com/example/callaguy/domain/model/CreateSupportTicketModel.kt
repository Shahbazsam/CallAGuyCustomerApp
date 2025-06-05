package com.example.callaguy.domain.model

import com.example.callaguy.data.dto.supportTicket.CreateSupportTicketDto

data class CreateSupportTicketModel(
    val serviceRequestId : Int,
    val issueType : String
){
    fun toDto() : CreateSupportTicketDto {
        return CreateSupportTicketDto(
            serviceRequestId = serviceRequestId,
            issueType = issueType
        )
    }
}
