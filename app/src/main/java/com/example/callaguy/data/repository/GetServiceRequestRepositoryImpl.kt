package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.serviceRequest.GetServiceRequestDto
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.GetServiceRequestRepository

class GetServiceRequestRepositoryImpl(
    private val apiService: ApiService
) : GetServiceRequestRepository {
    override suspend fun getServiceRequest(): List<GetServiceRequestDto?> {
        return apiService.getServiceRequests()
    }
}