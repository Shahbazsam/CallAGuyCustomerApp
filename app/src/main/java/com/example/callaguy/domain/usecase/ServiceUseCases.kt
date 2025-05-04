package com.example.callaguy.domain.usecase

import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.repository.ServiceRepository
import retrofit2.HttpException

class ServiceUseCases(
    private val serviceRepository: ServiceRepository
) {
    suspend fun getServices() : ResultClass<List<ServiceResponseDto>> {
        return try {
            val response = serviceRepository.getServices()
            ResultClass.Authorized(response)
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