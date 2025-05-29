package com.example.callaguy.domain.model

import com.example.callaguy.data.dto.serviceRequest.ServiceRequestStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class GetServiceRequestModel(
    val id: Int,
    val customerId: Int,
    val professionalId: Int?,
    val amount: BigDecimal,
    val subService: String,
    val subServiceId: Int,
    val status: ServiceRequestStatusModel,
    val preferredDate: LocalDate,
    val preferredTime: LocalTime,
    val address: String,
    val specialInstructions: String?,
    val createdAt: LocalDateTime
)

enum class ServiceRequestStatusModel {
    REQUESTED,
    ACCEPTED,
    COMPLETED,
    CANCELLED
}

fun ServiceRequestStatus.toModel() : ServiceRequestStatusModel {
    return ServiceRequestStatusModel.valueOf(this.name)
}
