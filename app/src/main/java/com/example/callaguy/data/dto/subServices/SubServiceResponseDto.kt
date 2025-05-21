package com.example.callaguy.data.dto.subServices

import com.example.callaguy.core.serialization.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class SubServiceResponseDto(
    val id : Int,
    val name : String,
    @Serializable(with = BigDecimalSerializer::class)
    val basePrice : BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val visitCharge : BigDecimal,
    val imageUrl : String? = null
)
