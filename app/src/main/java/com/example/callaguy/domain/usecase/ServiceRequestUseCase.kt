package com.example.callaguy.domain.usecase

import android.util.Log
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.ServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestResponseModel
import com.example.callaguy.domain.repository.ServiceRequestRepository
import retrofit2.HttpException

class ServiceRequestUseCase (
    private val serviceRequestRepository: ServiceRequestRepository
) {
    suspend fun createServiceRequest( data: ServiceRequestModel) : ResultClass<Unit> {
        return try {
            serviceRequestRepository.createServiceRequest(data)
            ResultClass.Authorized()
        }catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
}