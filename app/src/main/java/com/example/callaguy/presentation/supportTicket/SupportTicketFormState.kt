package com.example.callaguy.presentation.supportTicket

data class SupportTicketFormState(
    val serviceRequestId : Int? = null,
    val issue : String = "",
    val issueError : String? = null
)
