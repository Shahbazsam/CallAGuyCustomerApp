package com.example.callaguy.domain.usecase

import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.repository.SubServiceRepository
import retrofit2.HttpException


class SubServiceUseCase(
    private val subServiceRepository: SubServiceRepository
) {

    suspend fun getSubServices(id : Int) : ResultClass<List<SubServiceResponseDto>> {
       return try {
           val response = subServiceRepository.getSubServices(id)
           ResultClass.Authorized(response)
       }catch (e : HttpException) {
           when(e.code()) {
               401 -> ResultClass.Unauthorized()
               else -> ResultClass.UnKnownError()
           }
       }catch (e : Exception) {
           ResultClass.UnKnownError()
       }
    }
}