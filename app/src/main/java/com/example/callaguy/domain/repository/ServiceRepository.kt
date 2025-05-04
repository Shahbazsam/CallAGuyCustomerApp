package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.service.response.ServiceResponseDto

interface ServiceRepository {
    suspend fun getServices() : List<ServiceResponseDto>
}