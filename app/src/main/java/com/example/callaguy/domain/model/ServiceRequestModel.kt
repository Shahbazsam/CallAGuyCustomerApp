package com.example.callaguy.domain.model

import kotlinx.serialization.Contextual
import java.time.LocalDate
import java.time.LocalTime


data class ServiceRequestModel(
    val subServiceId : Int,
    val preferredDate : LocalDate,
    val preferredTime : LocalTime,
    val address : String,
    val specialInstructions : String?
)
