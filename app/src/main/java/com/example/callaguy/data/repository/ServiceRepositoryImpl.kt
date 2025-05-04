package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.ServiceRepository

class ServiceRepositoryImpl(
    private val apiService : ApiService
) : ServiceRepository {
    override suspend fun getServices(): List<ServiceResponseDto> {
        return apiService.getServices()
    }
}