package com.example.callaguy.presentation.serviceRequest

sealed class ServiceRequestFormEvent {
    data class AddressChanged(val address : String) : ServiceRequestFormEvent()
    data class InstructionChanged(val specialInstructions : String) : ServiceRequestFormEvent()

    data class Submit(val subServiceId : Int) : ServiceRequestFormEvent()
}