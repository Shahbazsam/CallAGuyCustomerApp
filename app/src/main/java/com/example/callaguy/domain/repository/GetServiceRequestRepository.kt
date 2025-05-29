package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.serviceRequest.GetServiceRequestDto

interface GetServiceRequestRepository {
    suspend fun getServiceRequest() : List<GetServiceRequestDto?>
}