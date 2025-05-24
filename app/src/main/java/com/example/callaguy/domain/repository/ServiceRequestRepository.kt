package com.example.callaguy.domain.repository

import com.example.callaguy.domain.model.ServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestResponseModel

interface ServiceRequestRepository {
    suspend fun createServiceRequest( data : ServiceRequestModel ) : ServiceRequestResponseModel
}