package com.example.callaguy.data.repository

import android.util.Log
import com.example.callaguy.data.dto.serviceRequest.CreateServiceRequest
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.model.ServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestResponseModel
import com.example.callaguy.domain.repository.ServiceRequestRepository

class ServiceRequestRepositoryImpl(
    private val apiService: ApiService
) : ServiceRequestRepository{

    override suspend fun createServiceRequest(data: ServiceRequestModel): ServiceRequestResponseModel {
        val response =  apiService.createServiceRequest(
            CreateServiceRequest(
                subServiceId = data.subServiceId,
                preferredDate = data.preferredDate,
                preferredTime = data.preferredTime,
                address = data.address,
                specialInstructions = data.specialInstructions
            )
        )
        Log.d("check1" , "$response , $data")
        return ServiceRequestResponseModel(
            message = response.message
        )
    }
}