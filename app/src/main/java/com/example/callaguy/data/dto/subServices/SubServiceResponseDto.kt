package com.example.callaguy.data.dto.subServices

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class SubServiceResponseDto(
    val id : Int,
    val name : String,
    @Contextual
    val basePrice : BigDecimal,
    @Contextual
    val visitCharge : BigDecimal,
    val imageUrl : String? = null
)
