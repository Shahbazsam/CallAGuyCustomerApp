package com.example.callaguy.presentation.serviceRequest

import java.time.LocalDate
import java.time.LocalTime

data class ServiceRequestFormState(
    val subServiceId : Int? = null,
    val preferredDate : LocalDate ? = null,
    val preferredTime : LocalTime? = null,
    val address : String = "",
    val specialInstructions : String? = null
)
