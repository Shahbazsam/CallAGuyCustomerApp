package com.example.callaguy.domain.usecase

import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.toModel
import com.example.callaguy.domain.repository.GetServiceRequestRepository
import retrofit2.HttpException


class GetServiceRequestUseCase(
    private val getServiceRequestRepository: GetServiceRequestRepository
) {
    suspend fun getServiceRequest() : ResultClass<List<GetServiceRequestModel?> >{
        return try {
            val response = getServiceRequestRepository.getServiceRequest().map { data ->
                data?.let {
                    GetServiceRequestModel(
                        id = data.id,
                        customerId = data.customerId,
                        professionalId = data.professionalId,
                        amount = data.amount,
                        subService = data.subService,
                        subServiceId = data.subServiceId,
                        status = data.status.toModel(),
                        preferredTime = data.preferredTime,
                        preferredDate = data.preferredDate,
                        address = data.address,
                        specialInstructions = data.specialInstructions,
                        createdAt = data.createdAt
                    )
                }
            }
            ResultClass.Authorized(response)
        } catch (e : HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
}