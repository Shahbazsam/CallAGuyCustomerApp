package com.example.callaguy.domain.usecase

import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.SendSupportMessageModel
import com.example.callaguy.domain.model.SupportMessagesModel
import com.example.callaguy.domain.repository.SupportMessageRepository
import retrofit2.HttpException

class SupportMessageUseCase(
    private val supportMessageRepository: SupportMessageRepository
) {
    suspend fun getSupportMessages( id : Int) : ResultClass<List<SupportMessagesModel>>{
        return try {
            val response  = supportMessageRepository.getSupportMessages(id)
            ResultClass.Authorized(
                response.map { message ->
                message.toModel()
                }
            )
        }catch ( e : HttpException ) {
            when(e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        }catch (e : Exception) {
            ResultClass.UnKnownError()
        }
    }

    suspend fun sendMessages( data : SendSupportMessageModel) : ResultClass<Unit> {
        return try {
            supportMessageRepository.sendMessage(
                data.toDto()
            )
            ResultClass.Authorized()
        }catch ( e : HttpException ) {
            when(e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        }catch (e : Exception) {
            ResultClass.UnKnownError()
        }
    }
}