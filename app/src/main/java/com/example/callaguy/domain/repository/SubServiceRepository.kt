package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.subServices.SubServiceResponseDto

interface SubServiceRepository {
    suspend fun getSubServices(id : Int) : List<SubServiceResponseDto>
}