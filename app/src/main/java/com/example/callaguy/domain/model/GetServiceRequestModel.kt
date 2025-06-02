package com.example.callaguy.domain.model

import com.example.callaguy.core.serialization.BigDecimalSerializer
import com.example.callaguy.core.serialization.LocalDateSerializer
import com.example.callaguy.core.serialization.LocalDateTimeSerializer
import com.example.callaguy.core.serialization.LocalTimeSerializer
import com.example.callaguy.data.dto.serviceRequest.ServiceRequestStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class GetServiceRequestModel(
    val id: Int,
    val customerId: Int,
    val professionalId: Int?,
    @Serializable( with = BigDecimalSerializer::class)
    val amount: BigDecimal,
    val subService: String,
    val subServiceId: Int,
    val status: ServiceRequestStatusModel,
    @Serializable(with = LocalDateSerializer::class)
    val preferredDate: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val preferredTime: LocalTime,
    val address: String,
    val specialInstructions: String?,
    @Serializable(with = LocalDateTimeSerializer::class)
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
