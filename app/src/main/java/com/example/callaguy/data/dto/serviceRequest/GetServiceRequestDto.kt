package com.example.callaguy.data.dto.serviceRequest

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class GetServiceRequestDto(
    val id: Int,
    val customerId: Int,
    val professionalId: Int?,
    @Contextual
    val amount: BigDecimal,
    val subService: String,
    val image : String?,
    val subServiceId: Int,
    val status: ServiceRequestStatus,
    @Contextual
    val preferredDate: LocalDate,
    @Contextual
    val preferredTime: LocalTime,
    val address: String,
    val specialInstructions: String?,
    @Contextual
    val createdAt: LocalDateTime
)


enum class ServiceRequestStatus {
    REQUESTED,
    ACCEPTED,
    COMPLETED,
    CANCELLED
}