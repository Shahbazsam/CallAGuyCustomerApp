package com.example.callaguy.presentation.supportTicket

sealed class SupportTicketFormEvent {

    data class IssueChange(val issue : String ) : SupportTicketFormEvent()

    data class Submit(val serviceRequestId : Int) : SupportTicketFormEvent()
}