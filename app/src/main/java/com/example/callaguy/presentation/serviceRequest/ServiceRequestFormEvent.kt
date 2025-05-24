package com.example.callaguy.presentation.serviceRequest

import java.time.LocalDate
import java.time.LocalTime

sealed class ServiceRequestFormEvent {
    data class DateChanged(val preferredDate : LocalDate ) : ServiceRequestFormEvent()
    data class TimeChange(val preferredTime : LocalTime) : ServiceRequestFormEvent()
    data class AddressChanged(val address : String) : ServiceRequestFormEvent()
    data class InstructionChanged(val specialInstructions : String?) : ServiceRequestFormEvent()

    data class Submit(val subServiceId : Int) : ServiceRequestFormEvent()
}