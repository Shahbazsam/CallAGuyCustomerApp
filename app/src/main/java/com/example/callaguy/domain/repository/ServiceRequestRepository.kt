package com.example.callaguy.domain.repository

import com.example.callaguy.domain.model.ServiceRequestModel

interface ServiceRequestRepository {
    suspend fun createServiceRequest( data : ServiceRequestModel )
}