package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.SubServiceRepository

class SubServiceRepositoryImpl(
    private val apiService: ApiService
) : SubServiceRepository {
    override suspend fun getSubServices(id: Int): List<SubServiceResponseDto> {
        return apiService.getSubServicesByServiceId(id)
    }
}